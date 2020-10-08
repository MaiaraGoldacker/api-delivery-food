insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into forma_pagamento (id, descricao) values (1, 'cartão de debito');
insert into forma_pagamento (id, descricao) values (2, 'cartão de crédito');
insert into forma_pagamento (id, descricao) values (3, 'dinheiro');

insert into restaurante (nome, taxa_frete, idcozinha) values ('aina', 10, 1);
insert into restaurante (nome, taxa_frete, idcozinha) values ('Casarão', 8, 2);

insert into estado (id, nome) values (1, 'Santa Catarina');
insert into estado (id, nome) values (2, 'Paraná');
insert into estado (id, nome) values (3, 'Rio Grande do Sul');


insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,1), (2,1);