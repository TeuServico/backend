<h1>🛠️ Como Rodar o Projeto Localmente</h1>
<p>Para executar este projeto localmente, é necessário:</p>
<ul>
  <li>Preencher corretamente as variáveis de ambiente.</li>
  <li>Gerar um par de chaves RSA para a geração de tokens JWT.</li>
</ul>

<h2>🗄️ Configuração do Banco de Dados PostgreSQL</h2>
<ul>
  <li><strong>URL do banco de dados:</strong><br><code>spring.datasource.url=${URL_DB}</code></li>
  <li><strong>Usuário do banco:</strong><br><code>spring.datasource.username=${USUARIO_DB}</code></li>
  <li><strong>Senha do banco:</strong><br><code>spring.datasource.password=${SENHA_DB}</code></li>
</ul>

<h2>🔐 Configuração de Criptografia AES</h2>
<ul>
  <li><strong>Chave secreta AES (16, 24 ou 32 caracteres):</strong><br><code>CRIPTOGRAFIA_SECRET_KEY=${CRIPTOGRAFIA_SECRET_KEY}</code></li>
</ul>

<h2>📧 Configuração do Serviço de E-mail (SMTP)</h2>
<ul>
  <li><strong>Host do servidor SMTP:</strong><br><code>spring.mail.host=${EMAIL_HOST}</code></li>
  <li><strong>Porta do servidor SMTP:</strong><br><code>spring.mail.port=${EMAIL_PORT}</code></li>
  <li><strong>Usuário do servidor SMTP:</strong><br><code>spring.mail.username=${EMAIL_USERNAME}</code></li>
  <li><strong>Senha do servidor SMTP:</strong><br><code>spring.mail.password=${EMAIL_PASSWORD}</code></li>
</ul>

<h2>☁️ Configuração da AWS</h2>
<ul>
  <li><strong>Endpoint da AWS:</strong><br><code>AWS_ENDPOINT=${AWS_ENDPOINT}</code></li>
  <li><strong>Chave de acesso AWS:</strong><br><code>AWS_ACESS_KEY=${AWS_ACESS_KEY}</code></li>
  <li><strong>Chave secreta AWS:</strong><br><code>AWS_SECRET_KEY=${AWS_SECRET_KEY}</code></li>
  <li><strong>Nome do bucket AWS:</strong><br><code>AWS_BUCKET_NAME=${AWS_BUCKET_NAME}</code></li>
</ul>

<h2>🔑 Geração de Chaves RSA para JWT</h2>
<p>Execute os comandos abaixo no terminal:</p>
<ul>
  <li><strong>Gerar chave privada:</strong><br><code>openssl genrsa -out app.key</code></li>
  <li><strong>Gerar chave pública a partir da chave privada:</strong><br><code>openssl rsa -in app.key -pubout -out app.pub</code></li>
</ul>

<p>📁 Coloque os arquivos <code>app.key</code> e <code>app.pub</code> dentro da pasta <code>resources</code>.</p>

<br>

<h1>📚 Documentação de Endpoints:</h1>

<h2>🌐 Base URL</h2>
<p>Todos os endpoints abaixo assumem a seguinte base para testes locais:</p>
<pre><code>http://localhost:8080</code></pre>

<h2>🔐 credenciais-usuario-controller</h2>

<h3><code>/credenciais/login</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Permite que um usuário realize login com e-mail e senha previamente cadastrados. Retorna um token JWT autenticado.</li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/credenciais/login
Content-Type: application/json

{
  "email": "usuario@exemplo.com",
  "senha": "senhaSegura123"
}</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600,
  "role": "PROFISSIONAL"
}</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Login realizado com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/credenciais/solicitar/resetsenha</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong> Gera um token de recuperação e envia um e-mail com o link para redefinir a senha.</li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/credenciais/solicitar/resetsenha?emailUsuario=usuario%40exemplo.com&linkRedefinicaoSenha=teuservico.com%2Fesquecersenha</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – E-mail de recuperação enviado com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/credenciais/resetsenha/inserirnovasenha</code></h3>
<ul>
  <li><strong>Método:</strong> <code>PUT</code></li>
  <li><strong>Descrição:</strong> Permite que o usuário redefina sua senha utilizando um token JWT de recuperação válido. Retorna um novo token JWT autenticado.</li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>PUT http://localhost:8080/credenciais/resetsenha/inserirnovasenha?novaSenha=senhanaosegura321&tokenJwtResetPassword=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvQGV4ZW1wbG8uY29tIiwiZXhwIjoxNzYzNDAzMTM5LCJ0eXBlIjoicmVzZXQtcGFzc</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600,
  "role": "PROFISSIONAL"
}</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Senha redefinida com sucesso e novo token gerado</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<h2>👤 cliente-controller</h2>

<h3><code>/cliente/criar</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Cria um novo cliente e retorna o token JWT de autenticação.</li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/cliente/criar
Content-Type: application/json 

{
  "credenciaisUsuarioRequestDTO": {
    "email": "usuariocliente@exemplo.com",
    "senha": "senhaSegura123"
  },
  "clienteRequestDTO": {
    "nomeCompleto": "João da Silva",
    "telefone": "81912345678",
    "cpf": "64479682090",
    "endereco": {
      "rua": "rua das Flores",
      "numero": "123",
      "complemento": "apto 202",
      "bairro": "Boa Viagem",
      "cidade": "Recife",
      "estado": "PE",
      "cep": "51030300"
    }
  }
}</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600,
  "role": "CLIENTE"
}</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Cliente criado com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/cliente/perfil</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong> Retorna os dados pessoais do cliente autenticado com token JWT.</li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/cliente/perfil
Authorization: Bearer &lt;seu_token_jwt&gt;</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "email": "joaosilva@gmail.com",
  "nomeCompleto": "João da Silva",
  "telefone": "81912345678",
  "cpf": "64479682090",
  "endereco": {
    "rua": "rua das Flores",
    "numero": "123",
    "complemento": "apto 202",
    "bairro": "Boa Viagem",
    "cidade": "Recife",
    "estado": "PE",
    "cep": "51030300"
  }
}</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Dados do cliente retornados com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<h2>🧑‍🔧 profissional-controller</h2>

<h3><code>/profissional/criar</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Cria um novo profissional e retorna o token JWT de autenticação.</li>
  <li><strong>Body campos principais:</strong>
    <ul>
      <li><code>credenciaisUsuarioRequestDTO</code> – Dados de login do profissional</li>
      <li><code>profissionalRequestDTO</code> – Dados pessoais, endereço, profissão e sobre mim</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/profissional/criar
Content-Type: application/json

{
  "credenciaisUsuarioRequestDTO": {
    "email": "usuarioprofissional@exemplo.com",
    "senha": "senhaSegura123"
  },
  "profissionalRequestDTO": {
    "nomeCompleto": "Rodrigo da Silva",
    "telefone": "81987654321",
    "cpf": "22559547023",
    "endereco": {
      "rua": "rua das Flores",
      "numero": "123",
      "complemento": "apto 202",
      "bairro": "boa Viagem",
      "cidade": "Recife",
      "estado": "PE",
      "cep": "51030300"
    },
    "sobreMim": "Programador apaixonado por resolver problemas com código. Especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. Café, código e criatividade são meu combustível diário.",
    "profissao": "PROGRAMADOR"
  }
}
</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "acessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 3600,
  "role": "PROFISSIONAL"
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Profissional criado com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/profissional/perfil</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong> Retorna os dados pessoais do profissional autenticado com token JWT.</li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/profissional/perfil
Authorization: Bearer &lt;seu_token_jwt&gt;</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "email": "rodrigosilva@gmail.com",
  "nomeCompleto": "rodrigo da Silva",
  "telefone": "81987654321",
  "cpf": "22559547023",
  "endereco": {
    "rua": "rua das Flores",
    "numero": "123",
    "complemento": "apto 202",
    "bairro": "boa Viagem",
    "cidade": "Recife",
    "estado": "PE",
    "cep": "51030300"
  },
  "sobreMim": "programador apaixonado por resolver problemas com código. especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. café, código e criatividade são meu combustível diário.",
  "profissao": "programador"
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Dados do profissional retornados com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<h2>🧩 tipo-servico-controller</h2>

<h3><code>/tiposervico/criar</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Permite a um profissional (autenticado via token JWT) criar um tipo de serviço que ainda não exista.</li>
  <li><strong>Body campos principais:</strong>
    <ul>
      <li><code>nome</code> – Nome do serviço (ex: "Desenvolver página web")</li>
      <li><code>categoria</code> – Categoria do serviço (ex: "PROGRAMAÇÃO")</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/tiposervico/criar
Content-Type: application/json
Authorization: Bearer &lt;seu_token_jwt&gt;
{
  "nome": "Desenvolver página web",
  "categoria": "PROGRAMAÇÃO"
}
</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "id": 1,
  "nome": "desenvolver página web",
  "categoria": "programação"
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Tipo de serviço criado com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/tiposervico/buscar/todos</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong> Retorna uma lista paginada com todos os tipos de serviço cadastrados.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>pagina</code> – Número da página (ex: <code>1</code>)</li>
      <li><code>qtdMaximaElementos</code> – Quantidade máxima de elementos por página (ex: <code>10</code>)</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/tiposervico/buscar/todos?pagina=1&qtdMaximaElementos=10</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "totalElementos": 10,
  "totalPaginas": 1,
  "paginaAtual": 1,
  "conteudo": [
    {
      "id": 1,
      "nome": "desenvolver página web",
      "categoria": "programação"
    },
    {
      "id": 2,
      "nome": "criar aplicativo mobile",
      "categoria": "programação"
    },
    {
      "id": 3,
      "nome": "instalar tomadas elétricas",
      "categoria": "eletricista"
    },
    {
      "id": 4,
      "nome": "pintura de parede interna",
      "categoria": "pintura"
    },
    {
      "id": 5,
      "nome": "reparo em encanamento",
      "categoria": "encanador"
    },
    {
      "id": 6,
      "nome": "montagem de móveis",
      "categoria": "marcenaria"
    },
    {
      "id": 7,
      "nome": "limpeza residencial",
      "categoria": "limpeza"
    },
    {
      "id": 8,
      "nome": "consultoria financeira",
      "categoria": "consultoria"
    },
    {
      "id": 9,
      "nome": "aulas de inglês",
      "categoria": "educação"
    },
    {
      "id": 10,
      "nome": "fotografia de eventos",
      "categoria": "fotografia"
    }
  ]
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Consulta realizada com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/tiposervico/buscar/categoria</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong> Retorna uma lista paginada com todos os tipos de serviço pertencentes a uma categoria específica.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>categoria</code> – Categoria que deseja filtrar (ex: <code>PROGRAMAÇÃO</code>)</li>
      <li><code>pagina</code> – Número da página (ex: <code>1</code>)</li>
      <li><code>qtdMaximaElementos</code> – Quantidade máxima de elementos por página (ex: <code>10</code>)</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/tiposervico/buscar/categoria?categoria=PROGRAMAÇÃO&pagina=1&qtdMaximaElementos=10</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "totalElementos": 2,
  "totalPaginas": 1,
  "paginaAtual": 1,
  "conteudo": [
    {
      "id": 1,
      "nome": "desenvolver página web",
      "categoria": "programação"
    },
    {
      "id": 2,
      "nome": "criar aplicativo mobile",
      "categoria": "programação"
    }
  ]
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Consulta realizada com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<h2>📦 oferta-servico-controller</h2>

<h3><code>/ofertaservico/criar</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Permite a um profissional(através de seu token de autenticação) criar uma oferta de serviço.</li>
  <li><strong>Body campos principais:</strong>
    <ul>
      <li><code>tipoServicoId</code> – ID do tipo de serviço</li>
      <li><code>descricao</code> – Descrição da oferta</li>
      <li><code>tags</code> – Lista de palavras-chave relacionadas</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/ofertaservico/criar
Content-Type: application/json
Authorization: Bearer &lt;seu_token_jwt&gt;

{
  "tipoServicoId": 1,
  "descricao": "Desenvolvimento de uma página web utilizando Java e React",
  "tags": [
    "Java",
    "Spring",
    "PostgreSQL",
    "React",
    "AWS S3"
  ]
}
</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "id": 0,
  "descricao": "Desenvolvimento de uma página web utilizando Java e React",
  "tags": [
    "Java",
    "Spring",
    "PostgreSQL",
    "React",
    "AWS S3"
  ],
  "tipoServico": {
    "id": 1,
    "nome": "desenvolver página web",
    "categoria": "programação"
  },
  "profissionalId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "profissionalNome": "Rodrigo da Silva",
  "profissionalSobreMim": "programador apaixonado por resolver problemas com código. especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. café, código e criatividade são meu combustível diário."
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – oferta de servico criado com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/ofertaservico/minhasofertas</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong> Permite a um profissional(através de seu token de autenticação) buscar todas as suas ofertas de serviço.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>pagina</code> – Número da página</li>
      <li><code>qtdMaximoElementos</code> – Quantidade máxima de elementos por página</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/ofertaservico/minhasofertas?pagina=1&qtdMaximoElementos=10
Authorization: Bearer &lt;seu_token_jwt&gt;</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "totalElementos": 1,
  "totalPaginas": 1,
  "paginaAtual": 1,
  "conteudo": [
    {
      "id": 0,
      "descricao": "Desenvolvimento de uma página web utilizando Java e React",
      "tags": [
        "Java",
        "Spring",
        "PostgreSQL",
        "React",
        "AWS S3"
      ],
      "tipoServico": {
        "id": 1,
        "nome": "desenvolver página web",
        "categoria": "programação"
      },
      "profissionalId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "profissionalNome": "Rodrigo da Silva",
      "profissionalSobreMim": "programador apaixonado por resolver problemas com código. especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. café, código e criatividade são meu combustível diário."
    }
  ]
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Consulta realizada com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/ofertaservico/buscar/tiposervico/nome</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong>(CONTAINING) Retorna uma lista paginada com todas ofertas pertencentes a um nome do tipo de serviço.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>pagina</code> – Número da página</li>
      <li><code>qtdMaximoElementos</code> – Quantidade máxima de elementos por página</li>
      <li><code>nome</code> – Nome do tipo de serviço</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/ofertaservico/buscar/tiposervico/nome?pagina=1&qtdMaximoElementos=10&nome=web</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "totalElementos": 2,
  "totalPaginas": 1,
  "paginaAtual": 1,
  "conteudo": [
    {
      "id": 402,
      "descricao": "Desenvolvimento de uma página web utilizando Java e React",
      "tags": [
        "java",
        "spring",
        "postgresql",
        "react",
        "aws s3"
      ],
      "tipoServico": {
        "id": 752,
        "nome": "desenvolver página web",
        "categoria": "programação"
      },
      "profissionalId": "1be3a467-faa1-46a7-8fed-2d4741cf529b",
      "profissionalNome": "rodrigo da silva",
      "profissionalSobreMim": "Programador apaixonado por resolver problemas com código. Especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. Café, código e criatividade são meu combustível diário."
    },
    {
      "id": 403,
      "descricao": "Desenvolvimento de uma página web utilizando Java e javascript",
      "tags": [
        "java",
        "javascript"
      ],
      "tipoServico": {
        "id": 752,
        "nome": "desenvolver página web",
        "categoria": "programação"
      },
      "profissionalId": "1be3a467-faa1-46a7-8fed-2d4741cf529b",
      "profissionalNome": "rodrigo da silva",
      "profissionalSobreMim": "Programador apaixonado por resolver problemas com código. Especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. Café, código e criatividade são meu combustível diário."
    }
  ]
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Consulta realizada com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/ofertaservico/buscar/tiposervico/categoria</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong> Retorna uma lista paginada com todas ofertas pertencentes a uma categoria do tipo de serviço.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>pagina</code> – Número da página</li>
      <li><code>qtdMaximoElementos</code> – Quantidade máxima de elementos por página</li>
      <li><code>categoria</code> – Categoria do tipo de serviço</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/ofertaservico/buscar/tiposervico/categoria?pagina=1&qtdMaximoElementos=10&categoria=PROGRAMAÇÃO</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "totalElementos": 2,
  "totalPaginas": 1,
  "paginaAtual": 1,
  "conteudo": [
    {
      "id": 402,
      "descricao": "Desenvolvimento de uma página web utilizando Java e React",
      "tags": [
        "java",
        "spring",
        "postgresql",
        "react",
        "aws s3"
      ],
      "tipoServico": {
        "id": 752,
        "nome": "desenvolver página web",
        "categoria": "programação"
      },
      "profissionalId": "1be3a467-faa1-46a7-8fed-2d4741cf529b",
      "profissionalNome": "rodrigo da silva",
      "profissionalSobreMim": "Programador apaixonado por resolver problemas com código. Especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. Café, código e criatividade são meu combustível diário."
    },
    {
      "id": 403,
      "descricao": "Desenvolvimento de uma página web utilizando Java e javascript",
      "tags": [
        "java",
        "javascript"
      ],
      "tipoServico": {
        "id": 752,
        "nome": "desenvolver página web",
        "categoria": "programação"
      },
      "profissionalId": "1be3a467-faa1-46a7-8fed-2d4741cf529b",
      "profissionalNome": "rodrigo da silva",
      "profissionalSobreMim": "Programador apaixonado por resolver problemas com código. Especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. Café, código e criatividade são meu combustível diário."
    }
  ]
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Consulta realizada com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/ofertaservico/buscar/contem/tags</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong> Retorna uma lista paginada com todas as ofertas de serviço que contenham ao menos uma das tags fornecidas.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>pagina</code> – Número da página</li>
      <li><code>qtdMaximoElementos</code> – Quantidade máxima de elementos por página</li>
      <li><code>tags</code> – Lista de tags (ex: <code>["java", "react"]</code>)</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/ofertaservico/buscar/contem/tags?pagina=1&qtdMaximoElementos=10&tags=javascript&tags=css&tags=html</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "totalElementos": 1,
  "totalPaginas": 1,
  "paginaAtual": 1,
  "conteudo": [
    {
      "id": 403,
      "descricao": "Desenvolvimento de uma página web utilizando Java e javascript",
      "tags": [
        "java",
        "javascript"
      ],
      "tipoServico": {
        "id": 752,
        "nome": "desenvolver página web",
        "categoria": "programação"
      },
      "profissionalId": "1be3a467-faa1-46a7-8fed-2d4741cf529b",
      "profissionalNome": "rodrigo da silva",
      "profissionalSobreMim": "Programador apaixonado por resolver problemas com código. Especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. Café, código e criatividade são meu combustível diário."
    }
  ]
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Consulta realizada com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<h2>📅 agendamento-controller</h2>

<h3><code>/agendamento/cliente/solicitar</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Permite a um cliente(através de seu token de autenticação) solicitar o agendamento de uma oferta de serviço.</li>
  <li><strong>Body campos principais:</strong>
    <ul>
      <li><code>ofertaServicoId</code> – ID da oferta de serviço</li>
      <li><code>dataEntrega</code> – Data desejada para entrega (formato: yyyy-MM-dd)</li>
      <li><code>observacoes</code> – Observações adicionais (opcional)</li>
      <li><code>precoDesejado</code> – Valor que o cliente deseja pagar</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/agendamento/cliente/solicitar
Content-Type: application/json
Authorization: Bearer &lt;seu_token_jwt&gt;

{
  "ofertaServicoId": 1,
  "dataEntrega": "2025-11-17",
  "observacoes": "string",
  "precoDesejado": 100
}</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "dataEntrega": "2025-11-17",
  "status": "AGUARDANDO_CONFIRMACAO_PROFISSIONAL",
  "observacoes": "string",
  "precoDesejado": 100,
  "temContraOferta": false,
  "contraOferta": {
    "contraOfertaDataDeEntrega": "2025-11-17",
    "contraOfertaPrecoDesejado": 0
  },
  "ofertaServicoResponseDTO": {
    "id": 1,
    "descricao": "Desenvolvimento de uma página web utilizando Java e React",
    "tags": ["Java", "Spring", "PostgreSQL", "React", "AWS S3"],
    "tipoServico": {
      "id": 1,
      "nome": "desenvolver página web",
      "categoria": "programação"
    },
    "profissionalId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "profissionalNome": "Rodrigo da Silva",
    "profissionalSobreMim": "programador apaixonado por resolver problemas com código. especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. café, código e criatividade são meu combustível diário."
  }
}</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Agendamento solicitado com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/agendamento/cliente/meusagendamentos</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong> Permite a um cliente(através de seu token de autenticação) obter todos os seus agendamentos.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>pagina</code> – Número da página</li>
      <li><code>qtdMaximaElementos</code> – Quantidade máxima de elementos por página</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/agendamento/cliente/meusagendamentos?pagina=1&qtdMaximaElementos=10
Authorization: Bearer &lt;seu_token_jwt&gt;</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "totalElementos": 1,
  "totalPaginas": 1,
  "paginaAtual": 1,
  "conteudo": [
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "dataEntrega": "2025-11-17",
      "status": "AGUARDANDO_CONFIRMACAO_PROFISSIONAL",
      "observacoes": "string",
      "precoDesejado": 100,
      "temContraOferta": false,
      "contraOferta": {
        "contraOfertaDataDeEntrega": "2025-11-17",
        "contraOfertaPrecoDesejado": 0
      },
      "ofertaServicoResponseDTO": {
        "id": 1,
        "descricao": "Desenvolvimento de uma página web utilizando Java e React",
        "tags": [
          "Java",
          "Spring",
          "PostgreSQL",
          "React",
          "AWS S3"
        ],
        "tipoServico": {
          "id": 1,
          "nome": "desenvolver página web",
          "categoria": "programação"
        },
        "profissionalId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "profissionalNome": "Rodrigo da Silva",
        "profissionalSobreMim": "programador apaixonado por resolver problemas com código. especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. café, código e criatividade são meu combustível diário."
      }
    }
  ]
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Busca realizada com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/agendamento/profissional/meusagendamentos</code></h3>
<ul>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>Descrição:</strong> Permite a um profissional(através de seu token de autenticação) obter todos os seus agendamentos.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>pagina</code> – Número da página</li>
      <li><code>qtdMaximaElementos</code> – Quantidade máxima de elementos por página</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>GET http://localhost:8080/agendamento/profissional/meusagendamentos?pagina=1&qtdMaximaElementos=10
Authorization: Bearer &lt;seu_token_jwt&gt;</code></pre>
  </li>
  <li><strong>Resposta:</strong>
    <pre><code>{
  "totalElementos": 1,
  "totalPaginas": 1,
  "paginaAtual": 1,
  "conteudo": [
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "dataEntrega": "2025-11-17",
      "status": "AGUARDANDO_CONFIRMACAO_PROFISSIONAL",
      "observacoes": "string",
      "precoDesejado": 100,
      "temContraOferta": false,
      "contraOferta": {
        "contraOfertaDataDeEntrega": "2025-11-17",
        "contraOfertaPrecoDesejado": 0
      },
      "ofertaServicoResponseDTO": {
        "id": 1,
        "descricao": "Desenvolvimento de uma página web utilizando Java e React",
        "tags": [
          "Java",
          "Spring",
          "PostgreSQL",
          "React",
          "AWS S3"
        ],
        "tipoServico": {
          "id": 1,
          "nome": "desenvolver página web",
          "categoria": "programação"
        },
        "profissionalId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "profissionalNome": "Rodrigo da Silva",
        "profissionalSobreMim": "programador apaixonado por resolver problemas com código. especialista em desenvolvimento web, sempre em busca de aprender novas tecnologias e criar soluções que fazem a diferença. café, código e criatividade são meu combustível diário."
      }
    }
  ]
}
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Busca realizada com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/agendamento/profissional/aceitar</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Permite a um profissional(através de seu token de autenticação) aceitar o agendamento feito por um cliente.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>idAgendamento</code> – ID do agendamento a ser aceito</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/agendamento/profissional/aceitar?idAgendamento=3fa85f64-5717-4562-b3fc-2c963f66afa6
Authorization: Bearer &lt;seu_token_jwt&gt;</code></pre>
  </li>
  <li><strong>Resposta:</strong>
  Envio de um email informando a confirmação do serviço
    <pre><code>
Agendamento aceito com sucesso, por favor faça a entrega do serviço dentro do prazo
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Agendamento aceito com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
       <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<h3><code>/agendamento/profissional/fazer/contraoferta</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Permite a um profissional(através de seu token de autenticação) fazer uma contra oferta para um agendamento feito por um cliente.</li>
  <li><strong>Body campos principais:</strong>
    <ul>
      <li><code>idDoAgendamento</code> – ID do agendamento original</li>
      <li><code>dataEntrega</code> – Nova data de entrega sugerida</li>
      <li><code>precoDesejado</code> – Novo valor sugerido</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/agendamento/profissional/fazer/contraoferta
Content-Type: application/json
Authorization: Bearer &lt;seu_token_jwt&gt;

{
  "idDoAgendamento": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "dataEntrega": "2025-11-30",
  "precoDesejado": 1000
}</code></pre>
  </li>
  <li><strong>Resposta:</strong>
  Envio de um email informando que foi feita uma contra oferta
    <pre><code>
Contra-proposta enviada com sucesso, aguarde a resposta do cliente
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Contra oferta de agendamento feita com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/agendamento/cliente/aceitar/contraoferta</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Permite a um cliente(através de seu token de autenticação) aceitar a contra oferta de agendamento feita por um profissional.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>idAgendamento</code> – ID do agendamento com contra oferta</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/agendamento/cliente/aceitar/contraoferta?idAgendamento=3fa85f64-5717-4562-b3fc-2c963f66afa6
Authorization: Bearer &lt;seu_token_jwt&gt;</code></pre>
  </li>
  <li><strong>Resposta:</strong>
   Envio de um email informando a confirmação do serviço
    <pre><code>
    Você aceitou a contra-proposta oferecida pelo profissional, aguarde até a conclusão do serviço
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Contra oferta de agendamento aceita com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/agendamento/cliente/cancelar</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Permite a um cliente(através de seu token de autenticação) cancelar um agendamento.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>idAgendamento</code> – ID do agendamento a ser cancelado</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/agendamento/cliente/cancelar?idAgendamento=3fa85f64-5717-4562-b3fc-2c963f66afa6
Authorization: Bearer &lt;seu_token_jwt&gt;</code></pre>
  </li>
  <li><strong>Resposta:</strong>
  Envio de um email informando o cancelamento do serviço
    <pre><code>
Agendamento cancelado com sucesso
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Agendamento cancelado com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/agendamento/profissional/cancelar</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Permite a um profissional(através de seu token de autenticação) cancelar um agendamento.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>idAgendamento</code> – ID do agendamento a ser cancelado</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/agendamento/profissional/cancelar?idAgendamento=3fa85f64-5717-4562-b3fc-2c963f66afa6
Authorization: Bearer &lt;seu_token_jwt&gt;</code></pre>
  </li>
  <li><strong>Resposta:</strong>
Envio de um email informando o cancelamento do serviço
    <pre><code>
Agendamento cancelado com sucesso
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Agendamento cancelado com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>

<hr>

<h3><code>/agendamento/profissional/concluir</code></h3>
<ul>
  <li><strong>Método:</strong> <code>POST</code></li>
  <li><strong>Descrição:</strong> Permite a um profissional(através de seu token de autenticação) concluir o agendamento.</li>
  <li><strong>Parâmetros:</strong>
    <ul>
      <li><code>idAgendamento</code> – ID do agendamento a ser concluído</li>
    </ul>
  </li>
  <li><strong>Exemplo (Postman):</strong>
    <pre><code>POST http://localhost:8080/agendamento/profissional/concluir?idAgendamento=3fa85f64-5717-4562-b3fc-2c963f66afa6
Authorization: Bearer &lt;seu_token_jwt&gt;</code></pre>
  </li>
  <li><strong>Resposta:</strong>
  Envio de um email informando a conclusão do serviço
    <pre><code>
Agendamento concluído com sucesso
</code></pre>
  </li>
  <li><strong>Erros comuns:</strong>
    <ul>
      <li><code>200 Ok Request</code> – Agendamento concluido com sucesso</li>
      <li><code>400 Bad Request</code> – Formato dos dados são inválidos</li>
      <li><code>401 Unauthorized</code> – Token ausente, inválido ou expirado</li>
      <li><code>403 Forbidden</code> – Usuário autenticado, mas sem permissão</li>
      <li><code>409 BusinessException</code> – Exceção de negócio</li>
      <li><code>500 Internal error</code> – Erro interno do servidor</li>
    </ul>
  </li>
</ul>