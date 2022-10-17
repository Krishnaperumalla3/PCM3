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

package com.pe.pcm.generator;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Chenchu Kiran.
 */
public class PasswordGenerator {

/*
   EFX Validation : (?=.*[A-z])(?=.*[A-Z])(?=.*[a-z])(?!.*[()+?,<>{}\[\]|;\\&])(?=.*[0-9])((?=(.*[!@#$%^&*]){2}))(?!.*([~]{5,}|"{5,}|:{5,}|[\.]{5,}|[\^]{5,}|[\-]{5,}|[\']{5,}|`{5,}|[\/]{5,}|!{5,}|@{5,}|#{5,}|[$]{5,}|%{5,}|[*]{5,}|_{5,}|={5,}|A{5,}|B{5,}|C{5,}|D{5,}|E{5,}|F{5,}|G{5,}|H{5,}|I{5,}|J{5,}|K{5,}|L{5,}|M{5,}|N{5,}|O{5,}|P{5,}|Q{5,}|R{5,}|S{5,}|T{5,}|U{5,}|V{5,}|W{5,}|X{5,}|Y{5,}|Z{5,}|a{5,}|b{5,}|c{5,}|d{5,}|e{5,}|f{5,}|g{5,}|h{5,}|i{5,}|j{5,}|k{5,}|l{5,}|m{5,}|n{5,}|o{5,}|p{5,}|q{5,}|r{5,}|s{5,}|t{5,}|u{5,}|v{5,}|w{5,}|x{5,}|y{5,}|z{5,}|0{5,}|1{5,}|2{5,}|3{5,}|4{5,}|5{5,}|6{5,}|7{5,}|8{5,}|9{5,}))\S{16,28}
*/

    private static final int MAX_LENGTH = 16;
    private static final String PASS_VALID_REGEX = "(?=.*[A-z])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])((?=(.*[`!@#$%^&*\\-_=\\+'/\\.,]){2}))\\S{16,28}";
    private static final Random random = new SecureRandom();

    private static final String DIGITS = "0123456789";
    private static final String LOCASE_CHARACTERS = "abcdefghjkmnpqrstuvwxyz";
    private static final String UPCASE_CHARACTERS = "ABCDEFGHJKMNPQRSTUVWXYZ";
    private static final String SYMBOLS = "!@#$%^&*_=+-/.?)"; //"!@#$%=:?"
    private static final String ALL = DIGITS + LOCASE_CHARACTERS + UPCASE_CHARACTERS + SYMBOLS;
    private static final char[] upperCaseArray = UPCASE_CHARACTERS.toCharArray();
    private static final char[] lowerCaseArray = LOCASE_CHARACTERS.toCharArray();
    private static final char[] digitsArray = DIGITS.toCharArray();
    private static final char[] symbolsArray = SYMBOLS.toCharArray();
    private static final char[] allArray = ALL.toCharArray();

    private PasswordGenerator() {
        //Not allow creating object from other classes
    }

    /**
     * Generate a random password based on security rules
     * <p>
     * - at least 8 characters, max of 16
     * - at least one uppercase
     * - at least one lowercase
     * - at least one number
     * - at least two symbols
     */
    private static String generatePassword() {
        StringBuilder sb = new StringBuilder();

        // get at least one lowercase letter
        sb.append(lowerCaseArray[random.nextInt(lowerCaseArray.length)]);

        // get at least one uppercase letter
        sb.append(upperCaseArray[random.nextInt(upperCaseArray.length)]);

        // get at least one symbol
        sb.append(symbolsArray[random.nextInt(symbolsArray.length)]);

        // get at least one digit
        sb.append(digitsArray[random.nextInt(digitsArray.length)]);

        // get at least one symbol
        sb.append(symbolsArray[random.nextInt(symbolsArray.length)]);

        // fill in remaining with random letters
        for (int i = 0; i < MAX_LENGTH - 4; i++) {
            sb.append(allArray[random.nextInt(allArray.length)]);
        }

        return sb.toString();
    }

    public static String generateValidPassword() {
        String password = generatePassword();
        if (password.matches(PASS_VALID_REGEX)) {
            return password;
        } else {
            return generateValidPassword();
        }
    }

    // Not Using.
    /*public String getRandomPassword(Integer upperCaseLettersCnt, Integer lowerCaseLettersCnt, Integer numberCnt, Integer specialCharCnt) {
         upperCaseLettersCnt = isNotNull(upperCaseLettersCnt) ? upperCaseLettersCnt : 2;
         lowerCaseLettersCnt = isNotNull(lowerCaseLettersCnt) ? lowerCaseLettersCnt : 2;
         numberCnt = isNotNull(numberCnt) ? numberCnt : 2;
         specialCharCnt = isNotNull(specialCharCnt) ? specialCharCnt : 2;
        if (Arrays.asList(upperCaseLettersCnt, lowerCaseLettersCnt, numberCnt, specialCharCnt).stream().reduce(0, Integer::sum) < 8) {
            throw internalServerError("Password length should not be less than 8.");
        }
        String upperCaseLetters1 = RandomStringUtils.random(upperCaseLettersCnt, 65, 90, true, true);
        String lowerCaseLetters1 = RandomStringUtils.random(lowerCaseLettersCnt, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(numberCnt);
        String specialChar1 = RandomStringUtils.random(specialCharCnt, 33, 47, false, false);
        String combinedChars = upperCaseLetters1.concat(lowerCaseLetters1).concat(numbers).concat(specialChar1);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }*/


}
