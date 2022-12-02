# VOTAÊ

## Equipe 

- Victor Hugo Grabowski Beltramini
- Bruce Vahldick

## Sistema 

- O sistema a ser desenvolvido consiste em um sistema de votações, que podera ser utilizado em diversos momentos e diversas pessoas. Por exemplo digamos que durante uma aula o professor deseje fazer uma votação com os alunos para decidir como irá funcionar o a avaliação do trabalho final, ele podera utilizar o sistema VotaÊ para fazer isso.

## Mensagens a serem trocadas pelo Server / Client

Client(Enviar Voto) -> Server(Recebe/Envia Confirmação de voto) -> Client

Conteúdo: { <br>
            idIntegrante: ID do integrante logado; <br>
            idPauta: ID da pauta que está sendo votada; <br>
            parecer: Favor ou Contra (String) <br>
          }

Descrição: Quando o voto é feito, está é a mensagem enviada ao servidor pelo VotingFacade

Retorno: { <br>
            sucesso: true ou false; <br>
            message: String com uma mensagem de erro/sucesso <br>
          }

## Requisitos

RF01 - O sistema deve permitir que seja possível criar um grupo de votantes <br>
RF02 - O sistema deve permitir que seja possível iniciar uma votação com os participantes de um grupo <br>
RF03 - O sistema deve permitir que seja possível para todos os participantes acompanhar os resultados preliminares em tempo real 
