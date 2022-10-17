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

package com.pe.pcm.workflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "PROCESS")
@JsonPropertyOrder(value= { "seqType", "flow", "processDocsList" })
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessModel implements Serializable{

	private static final long serialVersionUID = -5279172469108147894L;
	private String seqType;
	private String flow;

	private List<ProcessDocModel> processDocsList = new ArrayList<>();

	public ProcessModel() {	}

	public ProcessModel(String seqType, String flow, List<ProcessDocModel> processDocsList) {
		this.seqType = seqType;
		this.flow = flow;
		this.processDocsList = processDocsList;
	}

	@JacksonXmlProperty( localName = "SEQ_TYPE")
	public String getSeqType() {
		return seqType;
	}

	public void setSeqType(String seqType) {
		this.seqType = seqType;
	}

	@JacksonXmlProperty( localName = "FLOW")
	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	@JacksonXmlElementWrapper(localName = "PROCESS_DOCS")
	@JacksonXmlProperty(localName = "PROCESS_DOC")
	public List<ProcessDocModel> getProcessDocsList() {
		return processDocsList;
	}

	public void setProcessDocsList(List<ProcessDocModel> processDocsList) {
		this.processDocsList = processDocsList;
	}

	@Override
	public String toString() {
		return "ProcessModel{" +
				"seqType='" + seqType + '\'' +
				", flow='" + flow + '\'' +
				", processDocsList=" + processDocsList +
				'}';
	}
}
