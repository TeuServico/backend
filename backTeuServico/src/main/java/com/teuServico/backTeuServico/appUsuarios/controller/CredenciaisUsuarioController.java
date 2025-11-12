package com.teuServico.backTeuServico.appUsuarios.controller;

import com.teuServico.backTeuServico.appUsuarios.dto.CredenciaisUsuarioRequestDTO;
import com.teuServico.backTeuServico.appUsuarios.dto.TokenJWT;
import com.teuServico.backTeuServico.appUsuarios.service.CredenciaisUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("credenciais/")
public class CredenciaisUsuarioController {
    private final CredenciaisUsuarioService credenciaisUsuarioService;

    public CredenciaisUsuarioController(CredenciaisUsuarioService credenciaisUsuarioService) {
        this.credenciaisUsuarioService = credenciaisUsuarioService;
    }

    @Operation(
            summary = "Autenticação de usuário",
            description = "Permite que um usuário realize login com e-mail e senha previamente cadastrados. Retorna um token JWT autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @PostMapping("login")
    public TokenJWT login(@RequestBody CredenciaisUsuarioRequestDTO credenciaisUsuarioRequestDTO){
        return credenciaisUsuarioService.login(credenciaisUsuarioRequestDTO);
    }

    @Operation(
            summary = "Solicitação de redefinição de senha",
            description = "Gera um token de recuperação e envia um e-mail com o link para redefinir a senha."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E-mail de recuperação enviado com sucesso"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @GetMapping("solicitar/resetsenha")
    public ResponseEntity<String> solicitarResetSenha(@RequestParam String emailUsuario, @RequestParam String linkRedefinicaoSenha) {
        return credenciaisUsuarioService.esquecerSenha(emailUsuario, linkRedefinicaoSenha);
    }

    @Operation(
            summary = "Redefinir senha com token de recuperação",
            description = "Permite que o usuário redefina sua senha utilizando um token JWT de recuperação válido. Retorna um novo token JWT autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso e novo token gerado"),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/FalhaNaRequisicao"),
            @ApiResponse(responseCode = "409", ref = "#/components/responses/Conflito"),
            @ApiResponse(responseCode = "500", ref = "#/components/responses/ErroInterno")
    })
    @PutMapping("resetsenha/inserirnovasenha")
    public TokenJWT recuperarContaResetPassword(@RequestParam String novaSenha, @RequestParam String tokenJwtResetPassword) {
        return credenciaisUsuarioService.recuperarContaResetSenha(novaSenha, tokenJwtResetPassword);
    }

}