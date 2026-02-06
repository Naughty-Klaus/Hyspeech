package gg.ngl.hyspeech.util.param;

public class ParameterProcessor<C> {
    private final String key;
    private final Class<C> contextType;
    private final ParameterResolver<C> resolver;

    public ParameterProcessor(String key, Class<C> contextType, ParameterResolver<C> resolver) {
        this.key = key;
        this.contextType = contextType;
        this.resolver = resolver;
    }

    public String key() {
        return key;
    }

    public boolean supports(Object context) {
        return contextType.isInstance(context);
    }

    public String resolve(Object context) {
        return resolver.resolve(contextType.cast(context));
    }
}
