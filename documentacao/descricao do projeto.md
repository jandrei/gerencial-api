login com google
se vinculado a 1 organização, redireciona direto para dash da organizacao

se perfil associado
consulta motoclubes a qual participa
consulta transacoes vinculadas a seu nome 

se perfil tesoureiro ou presidente
consulta motoclubes a qual participa
consulta transacoes a todos associados do motoclube que ele esta logado
mantem cadastro de associados
mantem planos de cobrança e vigencias

se admin -> pode tudo que os demais tambem podem 
pode manter cadastro de organizacoes


Funcionalidades gerais
controle de pagamento de anuidades
despesas gerais com jantas e eventos
controle de inadinplencia dos associados para tesoureiro e presidente
integração com telegram ou whatsapp para avisos
carteirinha digital do associado
confirmação de presenca em eventos para associado
controle de inventário do moto clube
Mural de avisos, reuniao, eventos
como funciona o convite de associados? tesoureiro ou presidente cadastra email do associado vinculado a organizacao
quando o associado for fazer o login, se tiver mais que 1 convite pendente ele vincula conta gmail ao associado, atualiza nome e dados adicionais e obtem acesso aos dois moto clubes automaticamente.



Decisoes arquiteturais
utilizar cloudflare R2 para backup ou google drive
 - manter apenas ultimoas 30 dias de arquivos de backup -> monitorar e garantir que nao extrapole tamanho gratias :)
utilizar localweb vps ao inves de digital ocean droplets
lembrar de usar limite de memoria ao rodar aplicação com jar
Usar shadcn/ui + tailwind css com react para desenvolver no frontend