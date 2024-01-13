package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaClinica {
    static ArrayList<Paciente> pacientesCadastrados = new ArrayList<>();
    static ArrayList<Consulta> consultasAgendadas = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final String PACIENTES_FILE = "pacientes.txt";
    static final String CONSULTAS_FILE = "consultas.txt";

    public static void main(String[] args) {
        carregarDados(); // Carregar dados ao iniciar o programa

        int opcao;

        do {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarPaciente();
                    break;
                case 2:
                    marcarConsulta();
                    break;
                case 3:
                    cancelarConsulta();
                    break;
                case 4:
                    salvarDados(); // Salvar dados ao sair do programa
                    System.out.println("Saindo do sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 4);
    }

    static void exibirMenu() {
        System.out.println("\n===== Menu Principal =====");
        System.out.println("1. Cadastrar Paciente");
        System.out.println("2. Marcar Consulta");
        System.out.println("3. Cancelar Consulta");
        System.out.println("4. Sair");
    }

    static void cadastrarPaciente() {
        System.out.print("Digite o nome do paciente: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o telefone do paciente: ");
        String telefone = scanner.nextLine();

        // Verificar se o paciente já está cadastrado
        for (Paciente paciente : pacientesCadastrados) {
            if (paciente.telefone.equals(telefone)) {
                System.out.println("Paciente já cadastrado!");
                return; // Retorna ao menu principal
            }
        }

        Paciente novoPaciente = new Paciente(nome, telefone);
        pacientesCadastrados.add(novoPaciente);

        System.out.println("Paciente cadastrado com sucesso!");
    }

    static void marcarConsulta() {
        if (pacientesCadastrados.isEmpty()) {
            System.out.println("Não há pacientes cadastrados. Cadastre um paciente primeiro.");
            return;
        }

        System.out.println("===== Lista de Pacientes Cadastrados =====");
        for (int i = 0; i < pacientesCadastrados.size(); i++) {
            Paciente paciente = pacientesCadastrados.get(i);
            System.out.println(i + 1 + ". " + paciente.nome + " - Telefone: " + paciente.telefone);
        }

        System.out.print("Escolha o número correspondente ao paciente: ");
        int indicePaciente = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        if (indicePaciente < 1 || indicePaciente > pacientesCadastrados.size()) {
            System.out.println("Número de paciente inválido. Tente novamente.");
            return;
        }

        Paciente pacienteSelecionado = pacientesCadastrados.get(indicePaciente - 1);

        System.out.print("Digite a data da consulta (formato dd/MM/yyyy): ");
        String data = scanner.nextLine();

        System.out.print("Digite a hora da consulta (formato HH:mm): ");
        String hora = scanner.nextLine();

        // Tratamento de erros
        if (!validarData(data) || !validarHorario(hora) || !dataHoraFuturas(data, hora)) {
            System.out.println("Data ou hora inválida. Tente novamente.");
            return;
        }

        // Verificar se a hora e data não estão ocupadas
        for (Consulta consulta : consultasAgendadas) {
            if (consulta.data.equals(data) && consulta.hora.equals(hora)) {
                System.out.println("Já há uma consulta marcada nesse horário. Escolha outro horário.");
                return;
            }
        }

        System.out.print("Digite a especialidade desejada para a consulta: ");
        String especialidade = scanner.nextLine();

        Consulta novaConsulta = new Consulta(pacienteSelecionado, data, hora, especialidade);
        consultasAgendadas.add(novaConsulta);

        System.out.println("Consulta marcada com sucesso!");
    }

    static void cancelarConsulta() {
        if (consultasAgendadas.isEmpty()) {
            System.out.println("Não há consultas agendadas para cancelar.");
            return;
        }

        System.out.println("===== Lista de Consultas Agendadas =====");
        for (int i = 0; i < consultasAgendadas.size(); i++) {
            Consulta consulta = consultasAgendadas.get(i);
            System.out.println(i + 1 + ". " + consulta.paciente.nome + " - Data: " + consulta.data +
                    " Hora: " + consulta.hora + " Especialidade: " + consulta.especialidade);
        }

        System.out.print("Escolha o número correspondente à consulta que deseja cancelar: ");
        int indiceConsulta = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        if (indiceConsulta < 1 || indiceConsulta > consultasAgendadas.size()) {
            System.out.println("Número de consulta inválido. Tente novamente.");
            return;
        }

        Consulta consultaCancelada = consultasAgendadas.remove(indiceConsulta - 1);

        System.out.println("Consulta cancelada com sucesso para " + consultaCancelada.paciente.nome);
    }

    // Função para validar se a data é válida
    static boolean validarData(String data) {
        try {
            LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Função para validar se a hora é válida
    static boolean validarHorario(String hora) {
        return hora.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    // Função para validar se a data e hora são futuras
    static boolean dataHoraFuturas(String data, String hora) {
        LocalDate dataAtual = LocalDate.now();
        LocalTime horaAtual = LocalTime.now();

        // Convertendo String para LocalTime
        LocalTime horaConsulta = LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));

        if (dataAtual.isAfter(LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                || (dataAtual.isEqual(LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        && horaConsulta.isBefore(horaAtual))) {
            System.out.println("Você só pode marcar uma consulta em uma data posterior à de hoje.");
            return false;
        }

        return true;
    }

    // Função para carregar dados do arquivo
    static void carregarDados() {
        carregarPacientes();
        carregarConsultas();
    }

    // Função para salvar dados no arquivo
    static void salvarDados() {
        salvarPacientes();
        salvarConsultas();
    }

    // Função para carregar pacientes do arquivo
    static void carregarPacientes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PACIENTES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                pacientesCadastrados.add(new Paciente(parts[0], parts[1]));
            }
        } catch (IOException e) {
            // Pode não existir um arquivo ainda, e isso é aceitável
        }
    }

    // Função para salvar pacientes no arquivo
    static void salvarPacientes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PACIENTES_FILE))) {
            for (Paciente paciente : pacientesCadastrados) {
                writer.write(paciente.nome + "," + paciente.telefone);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Função para carregar consultas do arquivo
    static void carregarConsultas() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONSULTAS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Paciente paciente = pacientesCadastrados.stream()
                        .filter(p -> p.nome.equals(parts[0]))
                        .findFirst()
                        .orElse(null);

                if (paciente != null) {
                    consultasAgendadas.add(new Consulta(paciente, parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            // Pode não existir um arquivo ainda, e isso é aceitável
        }
    }

    // Função para salvar consultas no arquivo
    static void salvarConsultas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONSULTAS_FILE))) {
            for (Consulta consulta : consultasAgendadas) {
                writer.write(consulta.paciente.nome + "," + consulta.data + "," + consulta.hora + ","
                        + consulta.especialidade);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}