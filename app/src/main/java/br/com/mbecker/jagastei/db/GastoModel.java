package br.com.mbecker.jagastei.db;

import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;

public class GastoModel {
    private long id;
    private double valor;
    private Date quando;
    private String mesAno;
    private double lat;
    private double lng;
    private String obs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getQuando() {
        return quando;
    }

    public void setQuando(Long quando) {
        this.quando = new Date(quando);
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GastoModel that = (GastoModel) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @NonNull
    @Override
    public String toString() {
        return "GastoModel{" +
                "id=" + id +
                ", valor=" + valor +
                ", quando=" + quando +
                ", mesAno='" + mesAno + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", obs='" + obs + '\'' +
                '}';
    }

    public long getQuandoToTime() {
        if (quando != null)
            return quando.getTime();
        return 0;
    }
}
