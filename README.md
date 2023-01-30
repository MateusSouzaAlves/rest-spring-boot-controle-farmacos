 Uma Api rest para controle de funcionários e medicamentos em um ambiente hospitalar! 

Ela tem suporte para:

Cadastrar remédios;

Cadastrar funcionários;

Cadastrar usuários;

Adicionar ou remover quantidade de medicamentos;

Exclusão direta ou exclusão lógica dependendo da regra de negócio exigida pelo cliente.

Controle de segurança com liberação de requisição só após o login ser realizado e então a requisição passar a possuir o token para enviar como autenticação.

Toda essa implementação possui uma criptografia de senha via Bcrypt visando a segurança da instituição e suas funções!



Tecnologias utilizadas:

-Java;

-SpringBoot;

-TokenJWT;

-Flyway;

-MySql.

