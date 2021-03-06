set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;
delete from restaurante_usuario_responsavel;
delete from pedido;
delete from item_pedido;
delete from foto_produto;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');

insert into forma_pagamento (id, descricao) values (1, 'cartão de debito');
insert into forma_pagamento (id, descricao) values (2, 'cartão de crédito');
insert into forma_pagamento (id, descricao) values (3, 'dinheiro');

insert into estado (id, nome) values (1, 'Santa Catarina');
insert into estado (id, nome) values (2, 'Paraná');
insert into estado (id, nome) values (3, 'Rio Grande do Sul');

insert into cidade (id, nome, estado_id) values (1, 'Blumenau', 1);
insert into cidade (id, nome, estado_id) values (2, 'Curitiba', 2);

insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cep, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao, ativo, aberto) values ('aina', 10, 1, '89060300', '1750', 'casa', 'fidelis', 1, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values ('Casarão', 8, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true);

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,1), (2,1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 0, 1);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 0, 1);

insert into grupo (id, nome) values (1, 'Secretária');
insert into grupo (id, nome) values (2, 'Diretor');

insert into usuario (id, nome, email, senha, data_cadastro) values (1, 'Maiara', 'maiaragoldacker@gmail.com', '123', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (2, 'Bruno', 'brunohorn@gmail.com', '123', utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (3, 'Daniel', 'danielgold@gmail.com', '123', utc_timestamp);

insert into permissao (id, descricao, nome) values (1, 'TESTE_1', 'TESTE_1');
insert into permissao (id, descricao, nome) values (2, 'TESTE_2', 'TESTE_2');
insert into permissao (id, descricao, nome) values (3, 'TESTE_3', 'TESTE_3');

insert into grupo_permissao(grupo_id, permissao_id) values (1, 1);
insert into grupo_permissao(grupo_id, permissao_id) values (1, 2);
insert into grupo_permissao(grupo_id, permissao_id) values (1, 3);
insert into grupo_permissao(grupo_id, permissao_id) values (2, 3);

insert into usuario_grupo(usuario_id, grupo_id) values (1, 2);

insert into usuario_grupo(usuario_id, grupo_id) values (2, 1);
insert into usuario_grupo(usuario_id, grupo_id) values (2, 2);

insert into restaurante_usuario_responsavel(restaurante_id, usuario_id) values (1, 1);
insert into restaurante_usuario_responsavel(restaurante_id, usuario_id) values (2, 1);
insert into restaurante_usuario_responsavel(restaurante_id, usuario_id) values (2, 2);

insert into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, subtotal, taxa_frete, valor_total)
values (1,'babad7a1-6acb-11eb-9175-d09466bfafd8', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'ENTREGUE', utc_timestamp, 298.90, 10, 308.90);