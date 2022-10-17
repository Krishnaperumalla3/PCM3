/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.si.pwd.security.nis;

import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHKey;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.interfaces.DSAKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAKey;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PwdDisabledAlgorithmConstraints implements PwdAlgorithmConstraints {
    public static final String PROPERTY_CERTPATH_DISABLED_ALGS = "jdk.certpath.disabledAlgorithms";
    public static final String PROPERTY_TLS_DISABLED_ALGS = "jdk.tls.disabledAlgorithms";
    private static Map<String, String[]> disabledAlgorithmsMap = Collections.synchronizedMap(new HashMap());
    private static Map<String, PwdDisabledAlgorithmConstraints.KeySizeConstraints> keySizeConstraintsMap = Collections.synchronizedMap(new HashMap());
    private String[] disabledAlgorithms;
    private PwdDisabledAlgorithmConstraints.KeySizeConstraints keySizeConstraints;

    public PwdDisabledAlgorithmConstraints(String propertyName, String disabledAlgorithms) {
        synchronized(disabledAlgorithmsMap) {
            if (!disabledAlgorithmsMap.containsKey(propertyName)) {
                loadDisabledAlgorithmsMap(propertyName, disabledAlgorithms);
            }

            this.disabledAlgorithms = (String[])((String[])disabledAlgorithmsMap.get(propertyName));
            this.keySizeConstraints = (PwdDisabledAlgorithmConstraints.KeySizeConstraints)keySizeConstraintsMap.get(propertyName);
        }
    }

    public PwdDisabledAlgorithmConstraints(String[] disabledAlg) {
        if (disabledAlg == null) {
            disabledAlg = new String[0];
        }

        this.disabledAlgorithms = disabledAlg;
        this.keySizeConstraints = new PwdDisabledAlgorithmConstraints.KeySizeConstraints(disabledAlg);
    }

    public final int permits(Set<PwdCryptoPrimitive> primitives, String algorithm, AlgorithmParameters parameters) {
        if (primitives == null) {
            throw new IllegalArgumentException("No cryptographic primitives specified");
        } else if (algorithm != null && algorithm.length() != 0) {
            if (primitives != null && !primitives.isEmpty()) {
                Set<String> elements = null;
                String[] var5 = this.disabledAlgorithms;
                int var6 = var5.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    String disabled = var5[var7];
                    if (disabled != null && !disabled.isEmpty()) {
                        if (disabled.equalsIgnoreCase(algorithm)) {
                            return 1;
                        }

                        if (elements == null) {
                            elements = this.decomposes(algorithm);
                        }

                        Iterator var9 = elements.iterator();

                        while(var9.hasNext()) {
                            String element = (String)var9.next();
                            if (disabled.equalsIgnoreCase(element)) {
                                return 1;
                            }
                        }
                    }
                }

                return 0;
            } else {
                throw new IllegalArgumentException("No cryptographic primitive specified");
            }
        } else {
            throw new IllegalArgumentException("No algorithm name specified");
        }
    }

    public final int permits(Set<PwdCryptoPrimitive> primitives, Key key) {
        return this.checkConstraints(primitives, "", key, (AlgorithmParameters)null);
    }

    public final int permits(Set<PwdCryptoPrimitive> primitives, String algorithm, Key key, AlgorithmParameters parameters) {
        if (primitives == null) {
            throw new IllegalArgumentException("No cryptographic primitives specified");
        } else {
            return this.checkConstraints(primitives, algorithm, key, parameters);
        }
    }

    protected Set<String> decomposes(String algorithm) {
        if (algorithm != null && algorithm.length() != 0) {
            Pattern transPattern = Pattern.compile("/");
            String[] transTockens = transPattern.split(algorithm);
            Set<String> elements = new HashSet();
            String[] var5 = transTockens;
            int var6 = transTockens.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String transTocken = var5[var7];
                if (transTocken != null && transTocken.length() != 0) {
                    Pattern pattern = Pattern.compile("with|and", 2);
                    String[] tokens = pattern.split(transTocken);
                    String[] var11 = tokens;
                    int var12 = tokens.length;

                    for(int var13 = 0; var13 < var12; ++var13) {
                        String token = var11[var13];
                        if (token != null && token.length() != 0) {
                            elements.add(token);
                        }
                    }
                }
            }

            if (elements.contains("SHA1") && !elements.contains("SHA-1")) {
                elements.add("SHA-1");
            }

            if (elements.contains("SHA-1") && !elements.contains("SHA1")) {
                elements.add("SHA1");
            }

            if (elements.contains("SHA224") && !elements.contains("SHA-224")) {
                elements.add("SHA-224");
            }

            if (elements.contains("SHA-224") && !elements.contains("SHA224")) {
                elements.add("SHA224");
            }

            if (elements.contains("SHA256") && !elements.contains("SHA-256")) {
                elements.add("SHA-256");
            }

            if (elements.contains("SHA-256") && !elements.contains("SHA256")) {
                elements.add("SHA256");
            }

            if (elements.contains("SHA384") && !elements.contains("SHA-384")) {
                elements.add("SHA-384");
            }

            if (elements.contains("SHA-384") && !elements.contains("SHA384")) {
                elements.add("SHA384");
            }

            if (elements.contains("SHA512") && !elements.contains("SHA-512")) {
                elements.add("SHA-512");
            }

            if (elements.contains("SHA-512") && !elements.contains("SHA512")) {
                elements.add("SHA512");
            }

            return elements;
        } else {
            return new HashSet();
        }
    }

    private int checkConstraints(Set<PwdCryptoPrimitive> primitives, String algorithm, Key key, AlgorithmParameters parameters) {
        if (primitives == null) {
            throw new IllegalArgumentException("No cryptographic primitives specified");
        } else if (key == null) {
            throw new IllegalArgumentException("The key cannot be null");
        } else if (algorithm != null && algorithm.length() != 0 && this.permits(primitives, algorithm, parameters) == 1) {
            return 1;
        } else if (this.permits(primitives, key.getAlgorithm(), (AlgorithmParameters)null) == 1) {
            return 2;
        } else {
            return !this.keySizeConstraints.disables(key) ? 0 : 3;
        }
    }

    private static void loadDisabledAlgorithmsMap(String propertyName, String disabledAlgorithms) {
        String property = disabledAlgorithms;
        String[] algorithmsInProperty = null;
        if (disabledAlgorithms != null && !disabledAlgorithms.isEmpty()) {
            if (disabledAlgorithms.charAt(0) == '"' && disabledAlgorithms.charAt(disabledAlgorithms.length() - 1) == '"') {
                property = disabledAlgorithms.substring(1, disabledAlgorithms.length() - 1);
            }

            algorithmsInProperty = property.split(",");

            for(int i = 0; i < algorithmsInProperty.length; ++i) {
                algorithmsInProperty[i] = algorithmsInProperty[i].trim();
            }
        }

        if (algorithmsInProperty == null) {
            algorithmsInProperty = new String[0];
        }

        disabledAlgorithmsMap.put(propertyName, algorithmsInProperty);
        PwdDisabledAlgorithmConstraints.KeySizeConstraints keySizeConstraints = new PwdDisabledAlgorithmConstraints.KeySizeConstraints(algorithmsInProperty);
        keySizeConstraintsMap.put(propertyName, keySizeConstraints);
    }

    public static int getKeySize(Key key) {
        int size = 0;
        if (key instanceof RSAKey) {
            RSAKey pubk = (RSAKey)key;
            size = pubk.getModulus().bitLength();
        } else if (key instanceof ECKey) {
            ECKey pubk = (ECKey)key;
            size = pubk.getParams().getOrder().bitLength();
        } else if (key instanceof DSAKey) {
            DSAKey pubk = (DSAKey)key;
            size = pubk.getParams().getP().bitLength();
        } else if (key instanceof DHKey) {
            DHKey pubk = (DHKey)key;
            size = pubk.getParams().getP().bitLength();
        } else if (key instanceof SecretKey) {
            SecretKey sk = (SecretKey)key;
            if (sk.getFormat().equals("RAW") && sk.getEncoded() != null) {
                size = sk.getEncoded().length * 8;
            }
        }

        return size;
    }

    private static class KeySizeConstraints {
        private static final Pattern pattern = Pattern.compile("(\\S+)\\s+keySize\\s*(<=|<|==|!=|>|>=)\\s*(\\d+)");
        private Map<String, Set<PwdDisabledAlgorithmConstraints.KeySizeConstraint>> constraintsMap = Collections.synchronizedMap(new HashMap());

        public KeySizeConstraints(String[] restrictions) {
            String[] var2 = restrictions;
            int var3 = restrictions.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String restriction = var2[var4];
                if (restriction != null && !restriction.isEmpty()) {
                    Matcher matcher = pattern.matcher(restriction);
                    if (matcher.matches()) {
                        String algorithm = matcher.group(1);
                        PwdDisabledAlgorithmConstraints.KeySizeConstraint.Operator operator = PwdDisabledAlgorithmConstraints.KeySizeConstraint.Operator.of(matcher.group(2));
                        int length = Integer.parseInt(matcher.group(3));
                        algorithm = algorithm.toLowerCase(Locale.ENGLISH);
                        if (!this.constraintsMap.containsKey(algorithm)) {
                            this.constraintsMap.put(algorithm, new HashSet());
                        }

                        Set<PwdDisabledAlgorithmConstraints.KeySizeConstraint> constraintSet = (Set)this.constraintsMap.get(algorithm);
                        PwdDisabledAlgorithmConstraints.KeySizeConstraint constraint = new PwdDisabledAlgorithmConstraints.KeySizeConstraint(operator, length);
                        constraintSet.add(constraint);
                    }
                }
            }

        }

        public boolean disables(Key key) {
            String algorithm = key.getAlgorithm().toLowerCase(Locale.ENGLISH);
            if (this.constraintsMap.containsKey(algorithm)) {
                Set<PwdDisabledAlgorithmConstraints.KeySizeConstraint> constraintSet = (Set)this.constraintsMap.get(algorithm);
                Iterator var4 = constraintSet.iterator();

                while(var4.hasNext()) {
                    PwdDisabledAlgorithmConstraints.KeySizeConstraint constraint = (PwdDisabledAlgorithmConstraints.KeySizeConstraint)var4.next();
                    if (constraint.disables(key)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    private static class KeySizeConstraint {
        private int minSize;
        private int maxSize;
        private int prohibitedSize = -1;

        public KeySizeConstraint(PwdDisabledAlgorithmConstraints.KeySizeConstraint.Operator operator, int length) {
            switch(operator.ordinal()) {
                case 0:
                    this.minSize = 0;
                    this.maxSize = 2147483647;
                    this.prohibitedSize = length;
                    break;
                case 1:
                    this.minSize = length;
                    this.maxSize = length;
                    break;
                case 2:
                    this.minSize = length;
                    this.maxSize = 2147483647;
                    break;
                case 3:
                    this.minSize = length + 1;
                    this.maxSize = 2147483647;
                    break;
                case 4:
                    this.minSize = 0;
                    this.maxSize = length;
                    break;
                case 5:
                    this.minSize = 0;
                    this.maxSize = length > 1 ? length - 1 : 0;
                    break;
                default:
                    this.minSize = 2147483647;
                    this.maxSize = -1;
            }

        }

        public boolean disables(Key key) {
            int size = PwdDisabledAlgorithmConstraints.getKeySize(key);
            if (size <= 0) {
                return true;
            } else {
                return size < this.minSize || size > this.maxSize || this.prohibitedSize == size;
            }
        }

        static enum Operator {
            EQ,
            NE,
            LT,
            LE,
            GT,
            GE;

            private Operator() {
            }

            static PwdDisabledAlgorithmConstraints.KeySizeConstraint.Operator of(String s) {
                if (s.equals("==")) {
                    return EQ;
                } else if (s.equals("!=")) {
                    return NE;
                } else if (s.equals("<")) {
                    return LT;
                } else if (s.equals("<=")) {
                    return LE;
                } else if (s.equals(">")) {
                    return GT;
                } else if (s.equals(">=")) {
                    return GE;
                } else {
                    throw new IllegalArgumentException(s + " is not a legal Operator");
                }
            }
        }
    }
}
