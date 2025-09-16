import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizGameGUI extends JFrame implements ActionListener {
    private String[] questions = {
        "Who is known as the father of Java programming?",
        "Which planet is known as the Red Planet?",
        "What is the capital of France?",
        "Who wrote 'Romeo and Juliet'?",
        "Which data structure uses FIFO (First In First Out)?"
    };

    private String[][] options = {
        {"James Gosling", "Dennis Ritchie", "Bjarne Stroustrup", "Guido van Rossum"},
        {"Earth", "Mars", "Jupiter", "Venus"},
        {"Berlin", "Madrid", "Paris", "Rome"},
        {"Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"},
        {"Stack", "Queue", "Array", "Tree"}
    };

    private char[] answers = {'a', 'b', 'c', 'b', 'b'};
    private int index = 0;
    private int score = 0;

    private JLabel questionLabel;
    private JRadioButton[] choices = new JRadioButton[4];
    private ButtonGroup bg;
    private JButton nextButton;

    public QuizGameGUI() {
        setTitle("Quiz Game 🎮");
        setSize(600, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setBounds(30, 20, 500, 30);

        choices[0] = new JRadioButton();
        choices[1] = new JRadioButton();
        choices[2] = new JRadioButton();
        choices[3] = new JRadioButton();

        bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            choices[i].setBounds(50, 60 + (i * 40), 300, 30);
            bg.add(choices[i]);
            add(choices[i]);
        }

        nextButton = new JButton("Next ➡️");
        nextButton.setBounds(400, 200, 120, 30);
        nextButton.addActionListener(this);

        setLayout(null);
        add(questionLabel);
        add(nextButton);

        loadQuestion(index);
    }

    private void loadQuestion(int qIndex) {
        if (qIndex < questions.length) {
            questionLabel.setText((qIndex + 1) + ") " + questions[qIndex]);
            for (int i = 0; i < 4; i++) {
                choices[i].setText(options[qIndex][i]);
                choices[i].setSelected(false);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Quiz Over! \nYour Score: " + score + "/" + questions.length,
                    "Result", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        char selected = ' ';
        for (int i = 0; i < 4; i++) {
            if (choices[i].isSelected()) {
                selected = (char) ('a' + i);
            }
        }

        if (selected == answers[index]) {
            score++;
        }

        index++;
        loadQuestion(index);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuizGameGUI().setVisible(true);
        });
    }
}
