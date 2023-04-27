package io.micronaut.starter.core.test.feature.validation

import io.micronaut.starter.feature.validator.MicronautValidationFeature
import io.micronaut.starter.options.BuildTool
import io.micronaut.starter.options.Language
import io.micronaut.starter.test.CommandSpec
import io.micronaut.starter.test.LanguageBuildCombinations
import spock.lang.PendingFeature

class MicronautValidationSpec extends CommandSpec {

    void 'test validation feature for #lang and #buildTool'(Language lang, BuildTool buildTool) {
        given:
        generateProject(lang, buildTool, [MicronautValidationFeature.NAME])

        when:
        String output = executeBuild(buildTool, "test")

        then:
        output.contains("BUILD SUCCESS")

        where:
        [lang, buildTool] << LanguageBuildCombinations.combinations().findAll { !failingCombination(it) }
    }

    @PendingFeature
    void 'test validation feature for #lang and #buildTool'(Language lang, BuildTool buildTool) {
        given:
        generateProject(lang, buildTool, [MicronautValidationFeature.NAME])

        when:
        String output = executeBuild(buildTool, "test")

        then:
        output.contains("BUILD SUCCESS")

        where:
        [lang, buildTool] << LanguageBuildCombinations.combinations().findAll { failingCombination(it) }
    }

    boolean failingCombination(List l) {
        l[0] == Language.KOTLIN && l[1] == BuildTool.MAVEN
    }

    @Override
    String getTempDirectoryPrefix() {
        return "validation-app"
    }
}
