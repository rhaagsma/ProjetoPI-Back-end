package com.example.springboot.controllers;

import com.example.springboot.dtos.usuarioRecordDto;
import com.example.springboot.models.usuarioModel;
import com.example.springboot.repositories.UsuarioRepository;
import com.example.springboot.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("api/usuarios")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })

public class usuarioController {

    private final UsuarioService usuarioService;

    public usuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<usuarioModel> saveUsuario(@RequestBody @Valid usuarioRecordDto usuarioRecordDto) {

        usuarioModel savedUsuario = usuarioService.createUser(usuarioRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

//    public ResponseEntity<usuarioModel> saveUsuario(@RequestBody @Valid usuarioRecordDto usuarioRecordDto){//retorno = uma model do usuario
//        var usuarioModel = new usuarioModel();//cria uma model usuario, para salvar no bd
//        BeanUtils.copyProperties(usuarioRecordDto, usuarioModel);//copia o conteúdo da dto para a model criada
//        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioModel));//insere os valores da model na tabela usuarios e retorna se a inserção ocorreu com sucesso
//        //responseentity = status = se a operação ocorreu com sucesso, body = corpo(conteudo)
//    }

    @GetMapping("/usuarios")//SELECT * (seleciona todos os usuarios) (metodo GET)
    public ResponseEntity<List<usuarioModel>> getAllUsuarios(){//retorna um array de usuarios (todas as tuplas da tabela)
        List<usuarioModel> usuariosList = usuarioRepository.findAll();//lista com todos os usuarios
        if(!usuariosList.isEmpty()){//checa se a lista não está vazia
            for(usuarioModel usuario : usuariosList){//para cada usuario
                UUID id = usuario.getIdUsuario();
                usuario.add(linkTo(methodOn(usuarioController.class).getOneUsuario(id)).withSelfRel());//adiciona um link para cada resultado, redirecionando para a função getUsuarioById(id)
                //linkTo = define um link, MethodOn = indica onde está a função direcionada, bem como a função a ser chamada ao selecionar o link
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuariosList);
    }

    @GetMapping("/usuarios/{id}")//SELECT * FROM usuarios WHERE id={id} (metodo GET)
    public ResponseEntity<Object> getOneUsuario(@PathVariable(value="id") UUID id){//passa como parametro o id
        //PathVariable = usado para conseguir acessar o valor id
        Optional<usuarioModel> usuarioFound = usuarioRepository.findById(id);//recebe o resultado do select
        //optional = para usar funçoes prontas do java
        if(usuarioFound.isEmpty()){//se o objeto estiver vazio, não houve retorno do select
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado.");//usuario nao encontrado na database
        }
        usuarioFound.get().add(linkTo(methodOn(usuarioController.class).getAllUsuarios()).withSelfRel());//adiciona um link para o resultado, chamando a função para listar todos os usuarios
        return ResponseEntity.status(HttpStatus.OK).body(usuarioFound.get());//retorna o usuario encontrado
    }

    @PutMapping("/usuarios/{id}")//UPDATE INTO usuario WHERE id=id
    public ResponseEntity<Object> updateUsuario(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid usuarioRecordDto usuarioRecordDto){
        Optional<usuarioModel> usuarioFound = usuarioRepository.findById(id);//faz uma requisição de usuario no bd e salva num objeto
        if(usuarioFound.isEmpty()){//se o objeto está vazio
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado.");//usuario nao encontrado, impossivel atualizar
        }
        var usuarioModel = usuarioFound.get();//cria uma model usuario com os valores do usuario encontrado (copia os valores antigos)
        BeanUtils.copyProperties(usuarioRecordDto, usuarioModel);//copia o conteúdo da dto para a model criada (atualiza os valores, exceto id, pois usuario nao tem acesso)
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioModel));//salva a model no BD, atualizando os valores
    }

    @DeleteMapping("/usuarios/{id}")//DELETE FROM usuarios WHERE id=id
    public ResponseEntity<Object> deleteUsuario(@PathVariable(value="id") UUID id){
        Optional<usuarioModel> usuarioFound = usuarioRepository.findById(id);//consulta o usuario no BD
        if(usuarioFound.isEmpty()){//checa se o usuario não está no BD
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado.");//caso não, retorna erro
        }
        usuarioRepository.delete(usuarioFound.get());//deleta o usuario
        return ResponseEntity.status(HttpStatus.OK).body("Usuario Deletado com sucesso.");//retorna mensagem de sucesso
    }

}
