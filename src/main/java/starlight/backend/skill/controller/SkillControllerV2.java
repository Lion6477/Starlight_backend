package starlight.backend.skill.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import starlight.backend.proof.model.response.ProofWithSkills;
import starlight.backend.skill.model.request.AddSkill;
import starlight.backend.skill.service.SkillServiceInterface;
import starlight.backend.talent.model.response.TalentWithSkills;


@Slf4j
@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/v2")
@Tag(name = "Skill", description = "Skill API")
public class SkillControllerV2 {
    private SkillServiceInterface serviceService;

    @Operation(
            summary = "Add skill to Talent",
            description = "Add a Skill to a Talent, given the Talent ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                            implementation = TalentWithSkills.class
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasRole('TALENT')")
    @PostMapping("talents/{talent-id}/skills")
    public TalentWithSkills addSkillToTalent(@PathVariable("talent-id") long talentId,
                                             @RequestBody AddSkill skills,
                                             Authentication auth) {
        log.info("@PostMapping(\"talents/{talent-id}/skills\")");
        return serviceService.addSkillToTalent(talentId, skills, auth);
    }
}
