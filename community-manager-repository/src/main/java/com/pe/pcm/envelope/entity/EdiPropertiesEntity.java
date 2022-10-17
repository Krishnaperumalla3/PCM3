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

package com.pe.pcm.envelope.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "PETPE_EDIPROPERTIES")
public class EdiPropertiesEntity implements Serializable {

    private static final long serialVersionUID = 5928884067516081954L;

    @Id
    @JsonProperty(value = "pk_Id")
    @Column(name = "pk_Id")
    private String pkId;
    private String partnerid;
    private String partnername;
    private String direction;
    private String validateinput;
    private String validateoutput;
    private String isasenderidqal;
    private String isasenderid;
    private String isareceiveridqal;
    private String isareceiverid;
    private String isacontstd;
    private String isaauthinfo;
    private String isaauthinfoqual;
    private String isaauthsecinfo;
    private String isaauthsecqual;
    private String interversion;
    private String useindicator;
    private String segterm;
    private String subeleterm;
    private String eleterm;
    private String releasechar;
    private String retainenv;
    private String limitintersize;
    private String gssenderid;
    private String gsreceiverid;
    private String funcationalidcode;
    private String respagencycode;
    private String groupversion;
    private String stsenderid;
    private String streceiverid;
    private String geninack;
    private String ackdetlevel;
    private String trnsetidcode;
    private String encodedoc;
    private String streamseg;
    private String dataextraction;
    private String extractionmailbox;
    private String batchtransaction;
    private String complcheck;
    private String complcheckmap;
    private String hippacompliance;
    private String hippavallevel;
    private String businessprocess;
    private String invokebpforisa;
    private String bpinvokesetinpd;
    private String extractionmailboxbp;
    private String expectack;
    private String intackreq;
    private String usecorrelation;
    private String acceptlookalias;
    private String isaacceptlookalias;
    private String errorbp;
    private String acceptnoninter;
    private String spoutboundencode;
    private String ta1alaform;
    private String ala999form;
    private String globalcontno;
    private String accnongroup;
    private String ackdetails;
    private String edipostmode;
    private String errorbpmode;
    private String percontnumcheck;
    private String perdupnumcheck;
    private String invokebpmode;
    private String ackoverduehr;
    private String ackoverduemin;
    private String isaenvelopeid;
    private String isaenvelopename;
    private String gsenvelopeid;
    private String gsenvelopename;
    private String stenvelopeid;
    private String stenvelopename;
    private String lastupdatedby;
    @UpdateTimestamp
    private Timestamp updatedon;


    public String getPkId() {
        return pkId;
    }

    public EdiPropertiesEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public EdiPropertiesEntity setPartnerid(String partnerid) {
        this.partnerid = partnerid;
        return this;
    }

    public String getPartnername() {
        return partnername;
    }

    public EdiPropertiesEntity setPartnername(String partnername) {
        this.partnername = partnername;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public EdiPropertiesEntity setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public String getValidateinput() {
        return validateinput;
    }

    public EdiPropertiesEntity setValidateinput(String validateinput) {
        this.validateinput = validateinput;
        return this;
    }

    public String getValidateoutput() {
        return validateoutput;
    }

    public EdiPropertiesEntity setValidateoutput(String validateoutput) {
        this.validateoutput = validateoutput;
        return this;
    }

    public String getIsasenderidqal() {
        return isasenderidqal;
    }

    public EdiPropertiesEntity setIsasenderidqal(String isasenderidqal) {
        this.isasenderidqal = isasenderidqal;
        return this;
    }

    public String getIsasenderid() {
        return isasenderid;
    }

    public EdiPropertiesEntity setIsasenderid(String isasenderid) {
        this.isasenderid = isasenderid;
        return this;
    }

    public String getIsareceiveridqal() {
        return isareceiveridqal;
    }

    public EdiPropertiesEntity setIsareceiveridqal(String isareceiveridqal) {
        this.isareceiveridqal = isareceiveridqal;
        return this;
    }

    public String getIsareceiverid() {
        return isareceiverid;
    }

    public EdiPropertiesEntity setIsareceiverid(String isareceiverid) {
        this.isareceiverid = isareceiverid;
        return this;
    }

    public String getIsacontstd() {
        return isacontstd;
    }

    public EdiPropertiesEntity setIsacontstd(String isacontstd) {
        this.isacontstd = isacontstd;
        return this;
    }

    public String getIsaauthinfo() {
        return isaauthinfo;
    }

    public EdiPropertiesEntity setIsaauthinfo(String isaauthinfo) {
        this.isaauthinfo = isaauthinfo;
        return this;
    }

    public String getIsaauthinfoqual() {
        return isaauthinfoqual;
    }

    public EdiPropertiesEntity setIsaauthinfoqual(String isaauthinfoqual) {
        this.isaauthinfoqual = isaauthinfoqual;
        return this;
    }

    public String getIsaauthsecinfo() {
        return isaauthsecinfo;
    }

    public EdiPropertiesEntity setIsaauthsecinfo(String isaauthsecinfo) {
        this.isaauthsecinfo = isaauthsecinfo;
        return this;
    }

    public String getIsaauthsecqual() {
        return isaauthsecqual;
    }

    public EdiPropertiesEntity setIsaauthsecqual(String isaauthsecqual) {
        this.isaauthsecqual = isaauthsecqual;
        return this;
    }

    public String getInterversion() {
        return interversion;
    }

    public EdiPropertiesEntity setInterversion(String interversion) {
        this.interversion = interversion;
        return this;
    }

    public String getUseindicator() {
        return useindicator;
    }

    public EdiPropertiesEntity setUseindicator(String useindicator) {
        this.useindicator = useindicator;
        return this;
    }

    public String getSegterm() {
        return segterm;
    }

    public EdiPropertiesEntity setSegterm(String segterm) {
        this.segterm = segterm;
        return this;
    }

    public String getSubeleterm() {
        return subeleterm;
    }

    public EdiPropertiesEntity setSubeleterm(String subeleterm) {
        this.subeleterm = subeleterm;
        return this;
    }

    public String getEleterm() {
        return eleterm;
    }

    public EdiPropertiesEntity setEleterm(String eleterm) {
        this.eleterm = eleterm;
        return this;
    }

    public String getReleasechar() {
        return releasechar;
    }

    public EdiPropertiesEntity setReleasechar(String releasechar) {
        this.releasechar = releasechar;
        return this;
    }

    public String getRetainenv() {
        return retainenv;
    }

    public EdiPropertiesEntity setRetainenv(String retainenv) {
        this.retainenv = retainenv;
        return this;
    }

    public String getLimitintersize() {
        return limitintersize;
    }

    public EdiPropertiesEntity setLimitintersize(String limitintersize) {
        this.limitintersize = limitintersize;
        return this;
    }

    public String getGssenderid() {
        return gssenderid;
    }

    public EdiPropertiesEntity setGssenderid(String gssenderid) {
        this.gssenderid = gssenderid;
        return this;
    }

    public String getGsreceiverid() {
        return gsreceiverid;
    }

    public EdiPropertiesEntity setGsreceiverid(String gsreceiverid) {
        this.gsreceiverid = gsreceiverid;
        return this;
    }

    public String getFuncationalidcode() {
        return funcationalidcode;
    }

    public EdiPropertiesEntity setFuncationalidcode(String funcationalidcode) {
        this.funcationalidcode = funcationalidcode;
        return this;
    }

    public String getRespagencycode() {
        return respagencycode;
    }

    public EdiPropertiesEntity setRespagencycode(String respagencycode) {
        this.respagencycode = respagencycode;
        return this;
    }

    public String getGroupversion() {
        return groupversion;
    }

    public EdiPropertiesEntity setGroupversion(String groupversion) {
        this.groupversion = groupversion;
        return this;
    }

    public String getStsenderid() {
        return stsenderid;
    }

    public EdiPropertiesEntity setStsenderid(String stsenderid) {
        this.stsenderid = stsenderid;
        return this;
    }

    public String getStreceiverid() {
        return streceiverid;
    }

    public EdiPropertiesEntity setStreceiverid(String streceiverid) {
        this.streceiverid = streceiverid;
        return this;
    }

    public String getGeninack() {
        return geninack;
    }

    public EdiPropertiesEntity setGeninack(String geninack) {
        this.geninack = geninack;
        return this;
    }

    public String getAckdetlevel() {
        return ackdetlevel;
    }

    public EdiPropertiesEntity setAckdetlevel(String ackdetlevel) {
        this.ackdetlevel = ackdetlevel;
        return this;
    }

    public String getTrnsetidcode() {
        return trnsetidcode;
    }

    public EdiPropertiesEntity setTrnsetidcode(String trnsetidcode) {
        this.trnsetidcode = trnsetidcode;
        return this;
    }

    public String getEncodedoc() {
        return encodedoc;
    }

    public EdiPropertiesEntity setEncodedoc(String encodedoc) {
        this.encodedoc = encodedoc;
        return this;
    }

    public String getStreamseg() {
        return streamseg;
    }

    public EdiPropertiesEntity setStreamseg(String streamseg) {
        this.streamseg = streamseg;
        return this;
    }

    public String getDataextraction() {
        return dataextraction;
    }

    public EdiPropertiesEntity setDataextraction(String dataextraction) {
        this.dataextraction = dataextraction;
        return this;
    }

    public String getExtractionmailbox() {
        return extractionmailbox;
    }

    public EdiPropertiesEntity setExtractionmailbox(String extractionmailbox) {
        this.extractionmailbox = extractionmailbox;
        return this;
    }

    public String getBatchtransaction() {
        return batchtransaction;
    }

    public EdiPropertiesEntity setBatchtransaction(String batchtransaction) {
        this.batchtransaction = batchtransaction;
        return this;
    }

    public String getComplcheck() {
        return complcheck;
    }

    public EdiPropertiesEntity setComplcheck(String complcheck) {
        this.complcheck = complcheck;
        return this;
    }

    public String getComplcheckmap() {
        return complcheckmap;
    }

    public EdiPropertiesEntity setComplcheckmap(String complcheckmap) {
        this.complcheckmap = complcheckmap;
        return this;
    }

    public String getHippacompliance() {
        return hippacompliance;
    }

    public EdiPropertiesEntity setHippacompliance(String hippacompliance) {
        this.hippacompliance = hippacompliance;
        return this;
    }

    public String getHippavallevel() {
        return hippavallevel;
    }

    public EdiPropertiesEntity setHippavallevel(String hippavallevel) {
        this.hippavallevel = hippavallevel;
        return this;
    }

    public String getBusinessprocess() {
        return businessprocess;
    }

    public EdiPropertiesEntity setBusinessprocess(String businessprocess) {
        this.businessprocess = businessprocess;
        return this;
    }

    public String getInvokebpforisa() {
        return invokebpforisa;
    }

    public EdiPropertiesEntity setInvokebpforisa(String invokebpforisa) {
        this.invokebpforisa = invokebpforisa;
        return this;
    }

    public String getBpinvokesetinpd() {
        return bpinvokesetinpd;
    }

    public EdiPropertiesEntity setBpinvokesetinpd(String bpinvokesetinpd) {
        this.bpinvokesetinpd = bpinvokesetinpd;
        return this;
    }

    public String getExtractionmailboxbp() {
        return extractionmailboxbp;
    }

    public EdiPropertiesEntity setExtractionmailboxbp(String extractionmailboxbp) {
        this.extractionmailboxbp = extractionmailboxbp;
        return this;
    }

    public String getExpectack() {
        return expectack;
    }

    public EdiPropertiesEntity setExpectack(String expectack) {
        this.expectack = expectack;
        return this;
    }

    public String getIntackreq() {
        return intackreq;
    }

    public EdiPropertiesEntity setIntackreq(String intackreq) {
        this.intackreq = intackreq;
        return this;
    }

    public String getUsecorrelation() {
        return usecorrelation;
    }

    public EdiPropertiesEntity setUsecorrelation(String usecorrelation) {
        this.usecorrelation = usecorrelation;
        return this;
    }

    public String getAcceptlookalias() {
        return acceptlookalias;
    }

    public EdiPropertiesEntity setAcceptlookalias(String acceptlookalias) {
        this.acceptlookalias = acceptlookalias;
        return this;
    }

    public String getIsaacceptlookalias() {
        return isaacceptlookalias;
    }

    public EdiPropertiesEntity setIsaacceptlookalias(String isaacceptlookalias) {
        this.isaacceptlookalias = isaacceptlookalias;
        return this;
    }

    public String getErrorbp() {
        return errorbp;
    }

    public EdiPropertiesEntity setErrorbp(String errorbp) {
        this.errorbp = errorbp;
        return this;
    }

    public String getAcceptnoninter() {
        return acceptnoninter;
    }

    public EdiPropertiesEntity setAcceptnoninter(String acceptnoninter) {
        this.acceptnoninter = acceptnoninter;
        return this;
    }

    public String getSpoutboundencode() {
        return spoutboundencode;
    }

    public EdiPropertiesEntity setSpoutboundencode(String spoutboundencode) {
        this.spoutboundencode = spoutboundencode;
        return this;
    }

    public String getTa1alaform() {
        return ta1alaform;
    }

    public EdiPropertiesEntity setTa1alaform(String ta1alaform) {
        this.ta1alaform = ta1alaform;
        return this;
    }

    public String getAla999form() {
        return ala999form;
    }

    public EdiPropertiesEntity setAla999form(String ala999form) {
        this.ala999form = ala999form;
        return this;
    }

    public String getGlobalcontno() {
        return globalcontno;
    }

    public EdiPropertiesEntity setGlobalcontno(String globalcontno) {
        this.globalcontno = globalcontno;
        return this;
    }

    public String getAccnongroup() {
        return accnongroup;
    }

    public EdiPropertiesEntity setAccnongroup(String accnongroup) {
        this.accnongroup = accnongroup;
        return this;
    }

    public String getAckdetails() {
        return ackdetails;
    }

    public EdiPropertiesEntity setAckdetails(String ackdetails) {
        this.ackdetails = ackdetails;
        return this;
    }

    public String getEdipostmode() {
        return edipostmode;
    }

    public EdiPropertiesEntity setEdipostmode(String edipostmode) {
        this.edipostmode = edipostmode;
        return this;
    }

    public String getErrorbpmode() {
        return errorbpmode;
    }

    public EdiPropertiesEntity setErrorbpmode(String errorbpmode) {
        this.errorbpmode = errorbpmode;
        return this;
    }

    public String getPercontnumcheck() {
        return percontnumcheck;
    }

    public EdiPropertiesEntity setPercontnumcheck(String percontnumcheck) {
        this.percontnumcheck = percontnumcheck;
        return this;
    }

    public String getPerdupnumcheck() {
        return perdupnumcheck;
    }

    public EdiPropertiesEntity setPerdupnumcheck(String perdupnumcheck) {
        this.perdupnumcheck = perdupnumcheck;
        return this;
    }

    public String getInvokebpmode() {
        return invokebpmode;
    }

    public EdiPropertiesEntity setInvokebpmode(String invokebpmode) {
        this.invokebpmode = invokebpmode;
        return this;
    }

    public String getAckoverduehr() {
        return ackoverduehr;
    }

    public EdiPropertiesEntity setAckoverduehr(String ackoverduehr) {
        this.ackoverduehr = ackoverduehr;
        return this;
    }

    public String getAckoverduemin() {
        return ackoverduemin;
    }

    public EdiPropertiesEntity setAckoverduemin(String ackoverduemin) {
        this.ackoverduemin = ackoverduemin;
        return this;
    }

    public String getIsaenvelopeid() {
        return isaenvelopeid;
    }

    public EdiPropertiesEntity setIsaenvelopeid(String isaenvelopeid) {
        this.isaenvelopeid = isaenvelopeid;
        return this;
    }

    public String getIsaenvelopename() {
        return isaenvelopename;
    }

    public EdiPropertiesEntity setIsaenvelopename(String isaenvelopename) {
        this.isaenvelopename = isaenvelopename;
        return this;
    }

    public String getGsenvelopeid() {
        return gsenvelopeid;
    }

    public EdiPropertiesEntity setGsenvelopeid(String gsenvelopeid) {
        this.gsenvelopeid = gsenvelopeid;
        return this;
    }

    public String getGsenvelopename() {
        return gsenvelopename;
    }

    public EdiPropertiesEntity setGsenvelopename(String gsenvelopename) {
        this.gsenvelopename = gsenvelopename;
        return this;
    }

    public String getStenvelopeid() {
        return stenvelopeid;
    }

    public EdiPropertiesEntity setStenvelopeid(String stenvelopeid) {
        this.stenvelopeid = stenvelopeid;
        return this;
    }

    public String getStenvelopename() {
        return stenvelopename;
    }

    public EdiPropertiesEntity setStenvelopename(String stenvelopename) {
        this.stenvelopename = stenvelopename;
        return this;
    }

    public String getLastupdatedby() {
        return lastupdatedby;
    }

    public EdiPropertiesEntity setLastupdatedby(String lastupdatedby) {
        this.lastupdatedby = lastupdatedby;
        return this;
    }

    public Timestamp getUpdatedon() {
        return updatedon;
    }

    public EdiPropertiesEntity setUpdatedon(Timestamp updatedon) {
        this.updatedon = updatedon;
        return this;
    }
}
