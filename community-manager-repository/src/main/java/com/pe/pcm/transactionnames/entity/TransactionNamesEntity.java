package com.pe.pcm.transactionnames.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PETPE_TRANSACTION_NAMES")
public class TransactionNamesEntity extends Auditable implements Serializable {

    @Id
    private Integer seq;
    private String transactionName;


    public Integer getSeq() {
        return seq;
    }

    public TransactionNamesEntity setSeq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public TransactionNamesEntity setTransactionName(String transactionName) {
        this.transactionName = transactionName;
        return this;
    }
}
