package biblioteca.telas;

import biblioteca.model.Aluno;
import biblioteca.model.Emprestimo;
import biblioteca.model.Livro;
import biblioteca.services.AlunoService;
import biblioteca.services.EmprestimoService;
import biblioteca.services.LivroService;
import biblioteca.validations.Validador;

import javax.swing.*;


public class MenuEmprestimos extends MenuPrincipal {

    private EmprestimoService emprestimoService = new EmprestimoService();
    private LivroService livroService = new LivroService();
    private AlunoService alunoService = new AlunoService();



    private String msg = "Menu Empréstimos\n Escolha uma opção: \n 1 - Registrar Novo Empréstimo\n 2 - Registrar Devolução\n" +
            " 3 - Ver um Empréstimo\n 4 - Ver todos os Empréstimos\n 0 - Voltar ao menu principal";


    public void menuEmprestimos() {
        Validador validador = new Validador();
        Integer input;

        do {
            try {
                input = Integer.parseInt(JOptionPane.showInputDialog(msg));
            } catch (Exception e) {
                input = 9;
            }
        } while (!validador.validarInputMenu(input));


        int caminho = input;
        switch (caminho) {
            case 1 -> {
                TelaEmprestimoLivro el = new TelaEmprestimoLivro();
                System.out.println("Exibe elementos da tela: " + el.livro + el.aluno);
                System.out.println((el.livro).getClass());
                Livro livroE = livroService.buscarLivro(String.valueOf(el.livro));
                Aluno alunoE = alunoService.buscarAluno(String.valueOf(el.aluno));
                System.out.println(livroE); // Renba: Testando captura de livro e aluno
                System.out.println(alunoE);
                int codigoEmprestimo = emprestimoService.emprestar(el.data, el.dataDevolucao, alunoE, livroE);
                menuEmprestimos();
            }
            case 2 -> {
                TelaDevolucaoLivro dl = new TelaDevolucaoLivro();
                Livro livroD = livroService.buscarLivro(String.valueOf(dl.livro));
                Aluno alunoD = alunoService.buscarAluno(dl.aluno);
                emprestimoService.devolver(alunoD, livroD, dl.codigo);
                menuEmprestimos();
            }
            case 3 -> {
                int codEmp;
                do {
                    codEmp = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o código do Empréstimo "));
                } while (!validador.validarInputInteger(codEmp));
                Emprestimo emprestimoLocalizado = emprestimoService.buscarEmprestimo(codEmp); // Renba > ATT: Parâmetro (int 5) para testes
                String strEmprestimoLocalizado = emprestimoLocalizado.toString().replaceAll("\\[|\\,|\\]|\\_", "");
                JOptionPane.showMessageDialog(null, strEmprestimoLocalizado);
                menuEmprestimos();
            }
            case 4 -> { // Renba >> ATT Here 12/04
                JOptionPane.showMessageDialog(null, emprestimoService.listarEmprestimo());
                menuEmprestimos();
            }
            case 0 -> menuPrincipal();

            default -> {
                JOptionPane.showMessageDialog(null, "Opção Inválida");
                menuEmprestimos();
            }
        }

    }
}