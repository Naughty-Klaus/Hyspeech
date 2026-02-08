package gg.ngl.hyspeech.util.param;

@FunctionalInterface
public interface ParameterContextContributor {
    void contribute(ParameterContext ctx);
}
