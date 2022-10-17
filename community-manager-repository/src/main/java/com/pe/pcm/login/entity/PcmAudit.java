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

package com.pe.pcm.login.entity;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author Chenchu Kiran.
 */

@MappedSuperclass
public abstract class PcmAudit implements Serializable {


	@NotNull
	private Integer lockid;

	@UpdateTimestamp
	private Date createts;

	@UpdateTimestamp
	private Date modifyts;

	@NotNull
	private String createuserid;

	@NotNull
	private String modifyuserid;

	@NotNull
	private String createprogid;

	@NotNull
	private String modifyprogid;


	public Integer getLockid() {
		return lockid;
	}

	public void setLockid(Integer lockid) {
		this.lockid = lockid;
	}

	public Date getCreatets() {
		return createts;
	}

	public void setCreatets(Date createts) {
		this.createts = createts;
	}

	public Date getModifyts() {
		return modifyts;
	}

	public void setModifyts(Date modifyts) {
		this.modifyts = modifyts;
	}

	public String getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}

	public String getModifyuserid() {
		return modifyuserid;
	}

	public void setModifyuserid(String modifyuserid) {
		this.modifyuserid = modifyuserid;
	}

	public String getCreateprogid() {
		return createprogid;
	}

	public void setCreateprogid(String createprogid) {
		this.createprogid = createprogid;
	}

	public String getModifyprogid() {
		return modifyprogid;
	}

	public void setModifyprogid(String modifyprogid) {
		this.modifyprogid = modifyprogid;
	}

	@Override
	public String toString() {
		return "PcmAudit{" +
				"lockid=" + lockid +
				", createts=" + createts +
				", modifyts=" + modifyts +
				", createuserid='" + createuserid + '\'' +
				", modifyuserid='" + modifyuserid + '\'' +
				", createprogid='" + createprogid + '\'' +
				", modifyprogid='" + modifyprogid + '\'' +
				'}';
	}
}
