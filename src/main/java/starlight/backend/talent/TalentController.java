package starlight.backend.talent;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import starlight.backend.talent.model.response.TalentFullInfo;
import starlight.backend.talent.model.response.TalentPagePagination;
import starlight.backend.talent.model.response.TalentProfile;
import starlight.backend.talent.model.response.TalentSession;
import starlight.backend.talent.service.TalentServiceInterface;
import lombok.AllArgsConstructor;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Validated
public class TalentController {
    private TalentServiceInterface talentService;

    @GetMapping("/talents")
    public TalentPagePagination pagination (@RequestParam(defaultValue = "0") @Min(0) int page,
                                            @RequestParam(defaultValue = "10") @Positive int size) {
        return talentService.talentPagination(page, size);
    }

    @PreAuthorize("hasRole('TALENT')")
    @GetMapping("/talents/{talentId}")
    public Optional<TalentFullInfo> searchTalentById(@PathVariable(value = "talentId") Long talentId) {
        return talentService.talentFullInfo(talentId);
    }

    @PreAuthorize("hasRole('TALENT')")
    @PatchMapping("/talents/{talent-id}")
    public TalentProfile edit (@PathVariable(value = "talentId") Long talentId,
                                     @RequestBody TalentSession talentSession) {
        return talentService.editTalent(talentId, talentSession);
    }
}
