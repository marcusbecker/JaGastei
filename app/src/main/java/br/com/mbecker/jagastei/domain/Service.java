package br.com.mbecker.jagastei.domain;

import java.util.List;

import br.com.mbecker.jagastei.db.GastoModel;
import br.com.mbecker.jagastei.db.TagModel;

public interface Service {
    long salvarGasto(GastoModel g);

    List<GastoModel> listarGastos(String mesAno);

    List<TagModel> listarTags();

    void atualizaTags(long id, List<String> tags);
}
