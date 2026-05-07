package dev.langchain4j.model.anthropic;

import static dev.langchain4j.model.anthropic.InternalAnthropicCapabilities.supportsJsonSchemaResponseFormat;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class InternalAnthropicCapabilitiesTest {

    @ParameterizedTest
    @ValueSource(
            strings = {
                "claude-opus-4-7",
                "claude-opus-4-6",
                "claude-sonnet-4-6",
                "claude-opus-4-5-20251101",
                "claude-sonnet-4-5-20250929",
                "claude-haiku-4-5-20251001"
            })
    void supportsJsonSchemaResponseFormat_returnsTrue_forSupportedModelNames(String modelName) {
        assertThat(supportsJsonSchemaResponseFormat(modelName)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "claude-opus-4-1-20250805",
                "claude-opus-4-20250514",
                "claude-sonnet-4-20250514",
                "claude-3-5-sonnet-20240620",
                "some-future-model"
            })
    void supportsJsonSchemaResponseFormat_returnsFalse_forUnsupportedModelNames(String modelName) {
        assertThat(supportsJsonSchemaResponseFormat(modelName)).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void supportsJsonSchemaResponseFormat_returnsFalse_forNullOrEmpty(String modelName) {
        assertThat(supportsJsonSchemaResponseFormat(modelName)).isFalse();
    }
}
