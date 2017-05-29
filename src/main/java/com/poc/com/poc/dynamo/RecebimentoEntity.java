package com.poc.com.poc.dynamo;

import java.math.BigDecimal;
import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;

@DynamoDBTable(tableName = "Recebimento")
public class RecebimentoEntity {

    /** The id. */
    @DynamoDBHashKey(attributeName = "Id")
    @DynamoDBAutoGeneratedKey
    private String id;

    /** The concessionaria id. */
    @DynamoDBAttribute(attributeName = "tipo")
    private String tipo;

    @DynamoDBAttribute(attributeName = "nsu")
    private String nsu;

    @DynamoDBAttribute(attributeName = "conta")
    private String conta;

    @DynamoDBAttribute(attributeName = "formaPagamento")
    private String formaPagamento;

    @DynamoDBAttribute(attributeName = "valor")
    private BigDecimal valor;

    @DynamoDBAttribute(attributeName = "dataCriacao")
    private Date data;

    @DynamoDBAttribute(attributeName = "situacao")
    private String situacao;

    @DynamoDBAttribute(attributeName = "quantidadeTentativas")
    private Integer quantidadeTentativas;

    @DynamoDBAttribute(attributeName = "dataHoraUltimaTentativa")
    private Date dataHoraUltimaTentativa;

    @DynamoDBAttribute(attributeName = "motivo")
    private String motivo;

    // @DynamoDBAttribute(attributeName = "hashDuplicado")
    // @DynamoDBIndexHashKey(globalSecondaryIndexName = "hashDuplicado")
    // private Long hashDuplicado;

    @DynamoDBVersionAttribute
    private Long version;

    private String tableName;

    // public Long getHashDuplicado() {
    // return hashDuplicado;
    // }

    // public void setHashDuplicado(Long hashDuplicado) {
    // this.hashDuplicado = hashDuplicado;
    // }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNsu() {
        return nsu;
    }

    public void setNsu(String nsu) {
        this.nsu = nsu;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Integer getQuantidadeTentativas() {
        return quantidadeTentativas;
    }

    public void setQuantidadeTentativas(Integer quantidadeTentativas) {
        this.quantidadeTentativas = quantidadeTentativas;
    }

    public Date getDataHoraUltimaTentativa() {
        return dataHoraUltimaTentativa;
    }

    public void setDataHoraUltimaTentativa(Date dataHoraUltimaTentativa) {
        this.dataHoraUltimaTentativa = dataHoraUltimaTentativa;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((conta == null) ? 0 : conta.hashCode());
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((dataHoraUltimaTentativa == null) ? 0 : dataHoraUltimaTentativa.hashCode());
        result = prime * result + ((formaPagamento == null) ? 0 : formaPagamento.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((motivo == null) ? 0 : motivo.hashCode());
        result = prime * result + ((nsu == null) ? 0 : nsu.hashCode());
        result = prime * result + ((quantidadeTentativas == null) ? 0 : quantidadeTentativas.hashCode());
        result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
        result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RecebimentoEntity other = (RecebimentoEntity) obj;
        if (conta == null) {
            if (other.conta != null)
                return false;
        } else if (!conta.equals(other.conta))
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (dataHoraUltimaTentativa == null) {
            if (other.dataHoraUltimaTentativa != null)
                return false;
        } else if (!dataHoraUltimaTentativa.equals(other.dataHoraUltimaTentativa))
            return false;
        if (formaPagamento == null) {
            if (other.formaPagamento != null)
                return false;
        } else if (!formaPagamento.equals(other.formaPagamento))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (motivo == null) {
            if (other.motivo != null)
                return false;
        } else if (!motivo.equals(other.motivo))
            return false;
        if (nsu == null) {
            if (other.nsu != null)
                return false;
        } else if (!nsu.equals(other.nsu))
            return false;
        if (quantidadeTentativas == null) {
            if (other.quantidadeTentativas != null)
                return false;
        } else if (!quantidadeTentativas.equals(other.quantidadeTentativas))
            return false;
        if (situacao == null) {
            if (other.situacao != null)
                return false;
        } else if (!situacao.equals(other.situacao))
            return false;
        if (tableName == null) {
            if (other.tableName != null)
                return false;
        } else if (!tableName.equals(other.tableName))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        if (valor == null) {
            if (other.valor != null)
                return false;
        } else if (!valor.equals(other.valor))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RecebimentoEntity [id=" + id + ", tipo=" + tipo + ", nsu=" + nsu + ", conta=" + conta + ", formaPagamento=" + formaPagamento
                + ", valor=" + valor + ", data=" + data + ", situacao=" + situacao + ", quantidadeTentativas=" + quantidadeTentativas
                + ", dataHoraUltimaTentativa=" + dataHoraUltimaTentativa + ", motivo=" + motivo + ", version=" + version + ", tableName="
                + tableName + "]";
    }

    public static Long gerarHashDuplicado(RecebimentoEntity recebimentoEntity) {
        return Long.valueOf(recebimentoEntity.conta.hashCode() + recebimentoEntity.valor.hashCode() + recebimentoEntity.data.hashCode());
    }

}