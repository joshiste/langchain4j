package dev.langchain4j.model.anthropic;

import dev.langchain4j.Internal;
import java.util.Set;

@Internal
final class InternalAnthropicCapabilities {

    private static final Set<String> MODEL_NAME_PREFIXES_SUPPORTING_JSON_SCHEMA = Set.of(
            "claude-opus-4-7",
            "claude-opus-4-6",
            "claude-sonnet-4-6",
            "claude-opus-4-5",
            "claude-sonnet-4-5",
            "claude-haiku-4-5");

    private InternalAnthropicCapabilities() {}

    static boolean supportsJsonSchemaResponseFormat(String modelName) {
        if (modelName == null) {
            return false;
        }
        return MODEL_NAME_PREFIXES_SUPPORTING_JSON_SCHEMA.stream().anyMatch(modelName::startsWith);
    }
}
