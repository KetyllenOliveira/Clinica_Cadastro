# Clínica de Consultas Ágil

Este é um simples sistema de gestão de clínica médica desenvolvido em Java. Ele permite o cadastro de pacientes, agendamento de consultas e o cancelamento das mesmas. Além disso, o sistema incorpora a funcionalidade de persistência de dados, armazenando informações de pacientes e consultas em arquivos para que possam ser recuperadas em execuções subsequentes.

## Funcionalidades 🚀:

  1. Cadastro de Pacientes:
     - Registre novos pacientes informando nome e telefone. O sistema verifica se o paciente já está cadastrado para evitar duplicidade.

  2. Agendamento de Consultas:
     - Escolha um paciente já cadastrado;
     - Informe a data e a hora;
     - Selecione e especialidade desejada.
       
  3. Cancelamento de Consultas:
     - Visualize a lista de consultas agendadas e escolha qual deseja cancelar.

  4. Persistência dos Dados:
     - Os dados dos pacientes são armazenados no arquivo "pacientes.txt".
     - As informações das consultas são salvas no arquivo "consultas.txt".
     - Ao iniciar o programa, os dados são carregados a partir desses arquivos.

## Requisitos 🛠️:

- JDK (Java Development Kit) instalado para compilar e executar o programa.
- Terminal ou IDE Java para a execução do aplicativo

## Instruções de Uso 📋:

1. Compilação:
   - Execute o comando javac SistemaClinica.java no terminal para compilar o código.
2. Execução:
   - Após a compilação, execute java SistemaClinica para iniciar o programa.
3. Menu Principal:
   - O sistema exibirá um menu principal com as opções numeradas. Escolha a opção desejada digitando o número correspondente
4. Cadastro de Paciente:
   - Opção 1 para cadastrar paciente
5. Agendar Consulta:
  - Opção 2 para marcar consulta 
6. Cancelamento da Consulta :
  - Opção 03 para cancelar a consulta
7. Sair do Sistema:
  - Opção 4 para sair do sistema e salvar os dados

## Observações 📌:

- Os dados dos pacientes e consultas são armazenados nos arquivos "pacientes.txt" e "consultas.txt" no mesmo diretório do programa.
- Ao sair do sistema, os dados são automaticamente salvos.

Este sistema simples pode ser utilizado como ponto de partida para o desenvolvimento de uma aplicação mais robusta e completa. Sinta-se à vontade para expandir suas funcionalidades e personalizá-lo conforme necessário.
     
