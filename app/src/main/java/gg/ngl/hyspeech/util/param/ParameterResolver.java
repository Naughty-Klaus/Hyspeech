package gg.ngl.hyspeech.util.param;

@FunctionalInterface
public interface ParameterResolver<C> {
    String resolve(C context);
}
