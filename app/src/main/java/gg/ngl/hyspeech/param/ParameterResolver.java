package gg.ngl.hyspeech.param;

@FunctionalInterface
public interface ParameterResolver<C> {
    String resolve(C context);
}
