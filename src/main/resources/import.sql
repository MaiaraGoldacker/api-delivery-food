insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into forma_pagamento (id, descricao) values (1, 'cartão de debito');
insert into forma_pagamento (id, descricao) values (2, 'cartão de crédito');
insert into forma_pagamento (id, descricao) values (3, 'dinheiro');

insert into estado (id, nome) values (1, 'Santa Catarina');
insert into estado (id, nome) values (2, 'Paraná');
insert into estado (id, nome) values (3, 'Rio Grande do Sul');

insert into cidade (id, nome, idestado) values (1, 'Blumenau', 1);
insert into cidade (id, nome, idestado) values (2, 'Curitiba', 2);

insert into restaurante (nome, taxa_frete, idcozinha, endereco_cep, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade_id, data_cadastro, data_atualizacao) values ('aina', 10, 1, '89060300', '1750', 'casa', 'fidelis', 1, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, idcozinha, data_cadastro, data_atualizacao) values ('Casarão', 8, 2, utc_timestamp, utc_timestamp);

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,1), (2,1);