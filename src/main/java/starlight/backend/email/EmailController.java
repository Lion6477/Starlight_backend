package starlight.backend.email;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import starlight.backend.email.model.ChangePassword;
import starlight.backend.email.model.Email;
import starlight.backend.email.service.EmailService;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
@Tag(name = "Email", description = "Email related endpoints")
public class EmailController {

    private EmailService emailService;

    @Operation(
            summary = "send email",
            description = "send email for sponsor",
            tags = {"Email"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Exception.class)
                            )
                    )
            }
    )
    @PostMapping("/sponsors/{sponsor-id}/send")
    public void sendMail(@RequestBody Email email,
                         @PathVariable("sponsor-id") long sponsorId,
                         Authentication auth) {
        log.info("@PostMapping(\"/sponsors/{sponsor-id}/send\")");
        emailService.sendMail(email, sponsorId, auth);
    }

    @Operation(
            summary = "forgot password",
            description = "forgot password for sponsor",
            tags = {"Email"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Exception.class)
                            )
                    )
            }
    )
    @PostMapping("/sponsors/forgot-password")
    public void forgotPassword(HttpServletRequest request,
                               @RequestParam String email) {
        log.info("@PostMapping(\"/forgot-password\")");
        emailService.forgotPassword(request, email);
    }

    @Operation(
            summary = "recovery password",
            description = "recovery password for sponsor",
            tags = {"Email"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "CREATED"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Exception.class)
                            )
                    )
            }
    )
    @PostMapping("/sponsors/recovery-password")
    @ResponseStatus(HttpStatus.CREATED)
    public void recoveryPassword(@RequestParam String token,
                                 @RequestBody ChangePassword changePassword) {
        log.info("@PostMapping(\"/recovery-password\")");
        emailService.recoveryPassword(token, changePassword);
    }
}
