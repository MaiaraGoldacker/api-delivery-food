insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into forma_pagamento (id, descricao) values (1, 'cartão de debito')

insert into restaurante (nome, taxa_frete, idcozinha, idforma_pagamento) values ('aina', 10, 1, 1)
insert into restaurante (nome, taxa_frete, idcozinha, idforma_pagamento) values ('Casarão', 8, 2, 1)

insert into estado (id, nome) values (1, 'Santa Catarina');
insert into estado (id, nome) values (2, 'Paraná');
insert into estado (id, nome) values (3, 'Rio Grande do Sul');