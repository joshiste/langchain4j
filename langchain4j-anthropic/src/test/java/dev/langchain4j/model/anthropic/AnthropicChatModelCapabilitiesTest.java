package dev.langchain4j.model.anthropic;

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA;
import static org.assertj.core.api.Assertions.assertThat;

import dev.langchain4j.model.chat.Capability;
import java.util.Set;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class AnthropicChatModelCapabilitiesTest {

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
    void supportedCapabilities_includesJsonSchema_forStructuredOutputModels(String modelName) {
        AnthropicChatModel model = AnthropicChatModel.builder()
                .apiKey("dummy-key")
                .modelName(modelName)
                .build();

        assertThat(model.supportedCapabilities()).contains(RESPONSE_FORMAT_JSON_SCHEMA);
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
    void supportedCapabilities_excludesJsonSchema_forOlderOrUnknownModels(String modelName) {
        AnthropicChatModel model = AnthropicChatModel.builder()
                .apiKey("dummy-key")
                .modelName(modelName)
                .build();

        assertThat(model.supportedCapabilities()).doesNotContain(RESPONSE_FORMAT_JSON_SCHEMA);
    }

    @ParameterizedTest
    @EnumSource(
            value = AnthropicChatModelName.class,
            names = {
                "CLAUDE_OPUS_4_7",
                "CLAUDE_OPUS_4_6",
                "CLAUDE_SONNET_4_6",
                "CLAUDE_OPUS_4_5_20251101",
                "CLAUDE_SONNET_4_5_20250929",
                "CLAUDE_HAIKU_4_5_20251001"
            })
    void supportedCapabilities_includesJsonSchema_whenModelNameSetViaEnum(AnthropicChatModelName modelName) {
        AnthropicChatModel model = AnthropicChatModel.builder()
                .apiKey("dummy-key")
                .modelName(modelName)
                .build();

        assertThat(model.supportedCapabilities()).contains(RESPONSE_FORMAT_JSON_SCHEMA);
    }

    @ParameterizedTest
    @EnumSource(
            value = AnthropicChatModelName.class,
            names = {"CLAUDE_OPUS_4_1_20250805", "CLAUDE_OPUS_4_20250514", "CLAUDE_SONNET_4_20250514"})
    void supportedCapabilities_excludesJsonSchema_whenLegacyModelNameSetViaEnum(AnthropicChatModelName modelName) {
        AnthropicChatModel model = AnthropicChatModel.builder()
                .apiKey("dummy-key")
                .modelName(modelName)
                .build();

        assertThat(model.supportedCapabilities()).doesNotContain(RESPONSE_FORMAT_JSON_SCHEMA);
    }

    @ParameterizedTest
    @ValueSource(strings = {"claude-sonnet-4-6", "claude-opus-4-1-20250805"})
    void supportedCapabilities_preservesExplicitlyDeclaredCapabilities(String modelName) {
        AnthropicChatModel model = AnthropicChatModel.builder()
                .apiKey("dummy-key")
                .modelName(modelName)
                .supportedCapabilities(Set.of(RESPONSE_FORMAT_JSON_SCHEMA))
                .build();

        assertThat(model.supportedCapabilities()).contains(RESPONSE_FORMAT_JSON_SCHEMA);
    }

    @ParameterizedTest
    @ValueSource(strings = {"claude-sonnet-4-6"})
    void supportedCapabilities_isImmutable(String modelName) {
        AnthropicChatModel model = AnthropicChatModel.builder()
                .apiKey("dummy-key")
                .modelName(modelName)
                .build();

        Set<Capability> capabilities = model.supportedCapabilities();
        assertThat(capabilities).contains(RESPONSE_FORMAT_JSON_SCHEMA);
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> capabilities.add(RESPONSE_FORMAT_JSON_SCHEMA))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
