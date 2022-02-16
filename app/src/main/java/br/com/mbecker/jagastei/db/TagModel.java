package br.com.mbecker.jagastei.db;

import java.util.Arrays;

import br.com.mbecker.jagastei.util.TagUtil;


public class TagModel {
    private long id;
    private String tag;
    private Long[] gastos;

    public TagModel(long id, String tag, Long[] gastos) {
        this.id = id;
        this.tag = tag;
        this.gastos = gastos;
    }

    public TagModel(long id, String tag, String gastos) {
        this(id, tag, TagUtil.splitGastos(gastos));
    }

    public long getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public Long[] getGastos() {
        return gastos;
    }

    @Override
    public String toString() {
        return "TagModel{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", gastos=" + Arrays.toString(gastos) +
                '}';
    }
}
