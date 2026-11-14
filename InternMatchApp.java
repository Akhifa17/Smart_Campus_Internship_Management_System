import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class InternMatchApp extends JFrame {
    private final List<String[]> internships = new ArrayList<>();
    private final List<String[]> students = new ArrayList<>();
    // application: student email, internship index, status, progress (0-100)
    private final List<String[]> applications = new ArrayList<>();
    private String loggedInSkills = "";
    private String loggedInName = "";
    private String loggedInEmail = "";
    private boolean adminLoggedIn = false;

    private final Color BACKGROUND = new Color(13, 23, 41);
    private final Color CARD = new Color(25, 39, 64);
    private final Color TURQUOISE = new Color(44, 196, 181);
    private final Color WHITE = new Color(241, 245, 255);
    private final Color MUTED = new Color(166, 181, 206);

    public InternMatchApp() {
        setTitle("InternMatch | Find Your Future");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        showWelcomeScreen();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(TURQUOISE);
        button.setForeground(BACKGROUND);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private JPanel makePage(String headingText, String subtitleText) {
        JPanel page = new JPanel(new BorderLayout(15, 20));
        page.setBackground(BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        JPanel top = new JPanel(new GridLayout(2, 1, 4, 4));
        top.setOpaque(false);
        JLabel heading = new JLabel(headingText);
        heading.setFont(new Font("SansSerif", Font.BOLD, 28));
        heading.setForeground(WHITE);
        JLabel subtitle = new JLabel(subtitleText);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);
        top.add(heading);
        top.add(subtitle);
        page.add(top, BorderLayout.NORTH);
        return page;
    }

    private JButton makeBackButton(String text, Runnable action) {
        JButton button = new JButton(text);
        styleButton(button);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void showWelcomeScreen() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 45, 25, 45));

        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setOpaque(false);
        JLabel logo = new JLabel("InternMatch.");
        logo.setFont(new Font("SansSerif", Font.BOLD, 27));
        logo.setForeground(WHITE);
        JLabel tagline = new JLabel("YOUR CAREER JOURNEY STARTS HERE");
        tagline.setFont(new Font("SansSerif", Font.BOLD, 10));
        tagline.setForeground(MUTED);
        navbar.add(logo, BorderLayout.WEST);
        navbar.add(tagline, BorderLayout.EAST);
        mainPanel.add(navbar, BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setBackground(CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(43, 59, 84)),
            BorderFactory.createEmptyBorder(35, 35, 30, 35)));

        JLabel star = centeredLabel("✦", 38, TURQUOISE, true);
        JLabel welcome = centeredLabel("WELCOME TO INTERNMATCH", 12, TURQUOISE, true);
        JLabel heading1 = centeredLabel("Dream big.", 43, WHITE, true);
        JLabel heading2 = centeredLabel("Match your future.", 35, TURQUOISE, true);
        JLabel description = new JLabel("<html><div style='text-align:center;'>"
            + "Discover internships that match your skills.<br>"
            + "Find your gaps, grow your talent, and take<br>"
            + "the next step towards your dream career.</div></html>");
        description.setFont(new Font("SansSerif", Font.PLAIN, 14));
        description.setForeground(MUTED);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton start = new JButton("Get Started  →");
        styleButton(start);
        start.setPreferredSize(new Dimension(190, 48));
        start.setMaximumSize(new Dimension(190, 48));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.addActionListener(e -> showOptions());

        JLabel footer = centeredLabel("YOUR SKILLS. YOUR FUTURE. YOUR MATCH.", 10, MUTED, true);
        card.add(star);
        card.add(Box.createVerticalStrut(10));
        card.add(welcome);
        card.add(Box.createVerticalStrut(15));
        card.add(heading1);
        card.add(heading2);
        card.add(Box.createVerticalStrut(15));
        card.add(description);
        card.add(Box.createVerticalStrut(25));
        card.add(start);
        card.add(Box.createVerticalStrut(22));
        card.add(footer);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(card);
        mainPanel.add(center, BorderLayout.CENTER);
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    private JLabel centeredLabel(String text, int size, Color color, boolean bold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", bold ? Font.BOLD : Font.PLAIN, size));
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void showOptions() {
        JDialog dialog = new JDialog(this, "Let's Get Started", true);
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 12));
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        JButton login = new JButton("Student Login");
        JButton register = new JButton("Create Student Account");
        JButton admin = new JButton("Admin Login");
        JButton back = new JButton("Back to Welcome");
        for (JButton b : new JButton[]{login, register, admin, back}) {
            styleButton(b);
            panel.add(b);
        }
        login.addActionListener(e -> studentLogin(dialog));
        register.addActionListener(e -> studentRegister());
        admin.addActionListener(e -> adminLogin(dialog));
        back.addActionListener(e -> dialog.dispose());
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void studentRegister() {
        JTextField name = new JTextField(20);
        JTextField email = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JTextField skills = new JTextField(20);
        JPanel p = new JPanel(new GridLayout(4, 2, 8, 8));
        p.add(new JLabel("Full Name:")); p.add(name);
        p.add(new JLabel("Email:")); p.add(email);
        p.add(new JLabel("Password:")); p.add(password);
        p.add(new JLabel("Skills (comma-separated):")); p.add(skills);

        if (JOptionPane.showConfirmDialog(this, p, "Create Student Account",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

        String n = name.getText().trim(), em = email.getText().trim();
        String pw = new String(password.getPassword()), sk = skills.getText().trim();
        if (n.isEmpty() || em.isEmpty() || pw.isEmpty() || sk.isEmpty()) {
            message("Please fill in all fields.");
            return;
        }
        for (String[] student : students) {
            if (student[1].equalsIgnoreCase(em)) {
                message("An account with this email already exists.");
                return;
            }
        }
        students.add(new String[]{n, em, pw, sk});
        message("Student registered successfully! You can now log in.");
    }

    private void studentLogin(JDialog dialog) {
        JTextField email = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JPanel p = new JPanel(new GridLayout(2, 2, 8, 8));
        p.add(new JLabel("Email:")); p.add(email);
        p.add(new JLabel("Password:")); p.add(password);
        if (JOptionPane.showConfirmDialog(this, p, "Student Login",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;

        String em = email.getText().trim(), pw = new String(password.getPassword());
        for (String[] student : students) {
            if (student[1].equalsIgnoreCase(em) && student[2].equals(pw)) {
                loggedInName = student[0];
                loggedInEmail = student[1];
                loggedInSkills = student[3];
                dialog.dispose();
                showStudentDashboard();
                return;
            }
        }
        message("Invalid email or password!");
    }

    private void adminLogin(JDialog dialog) {
        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JPanel p = new JPanel(new GridLayout(2, 2, 8, 8));
        p.add(new JLabel("Admin Username:")); p.add(username);
        p.add(new JLabel("Password:")); p.add(password);
        if (JOptionPane.showConfirmDialog(this, p, "Admin Login",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        if (username.getText().trim().equals("admin")
                && new String(password.getPassword()).equals("admin123")) {
            adminLoggedIn = true;
            dialog.dispose();
            showAdminDashboard();
        } else {
            message("Incorrect username or password!");
        }
    }

    private void showAdminDashboard() {
        JPanel page = makePage("Admin Dashboard", "Manage internships and student applications");
        JPanel buttons = new JPanel(new GridLayout(5, 1, 0, 12));
        buttons.setOpaque(false);
        String[] labels = {"Manage Internships", "View Saved Internships",
            "View Student Applications", "Update Application Status", "← Back to Welcome"};
        for (String label : labels) {
            JButton b = new JButton(label);
            styleButton(b);
            b.setBackground(CARD);
            b.setForeground(WHITE);
            buttons.add(b);
            switch (label) {
                case "Manage Internships": b.addActionListener(e -> showManageInternships()); break;
                case "View Saved Internships": b.addActionListener(e -> showAllInternships()); break;
                case "View Student Applications": b.addActionListener(e -> showApplications(true)); break;
                case "Update Application Status": b.addActionListener(e -> updateApplicationStatus()); break;
                default: b.addActionListener(e -> showWelcomeScreen());
            }
        }
        page.add(buttons, BorderLayout.CENTER);
        setContentPane(page);
        revalidate(); repaint();
    }

    private void showManageInternships() {
        JTextField title = new JTextField(), company = new JTextField();
        JTextField skills = new JTextField(), stipend = new JTextField();
        Object[] fields = {"Internship Title:", title, "Company Name:", company,
            "Required Skills (comma-separated):", skills, "Monthly Stipend (₹):", stipend};
        if (JOptionPane.showConfirmDialog(this, fields, "Add New Internship",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        String t = title.getText().trim(), c = company.getText().trim();
        String s = skills.getText().trim(), st = stipend.getText().trim();
        if (t.isEmpty() || c.isEmpty() || s.isEmpty() || st.isEmpty()) {
            message("Please fill in all fields."); return;
        }
        internships.add(new String[]{t, c, s, st});
        message("Internship saved successfully!");
    }

    private void showAllInternships() {
        if (internships.isEmpty()) { message("No internships added yet."); return; }
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < internships.size(); i++) {
            String[] in = internships.get(i);
            text.append(i + 1).append(". ").append(in[0]).append("\nCompany: ")
                .append(in[1]).append("\nRequired skills: ").append(in[2])
                .append("\nStipend: ₹").append(in[3]).append("\n\n");
        }
        showText("Available Internships", text.toString());
    }

    private Set<String> skillSet(String skills) {
        Set<String> set = new LinkedHashSet<>();
        for (String skill : skills.split(",")) {
            String cleaned = skill.trim().toLowerCase(Locale.ROOT);
            if (!cleaned.isEmpty()) set.add(cleaned);
        }
        return set;
    }

    private void showSkillMatch() {
        if (internships.isEmpty()) { message("No internships available yet."); return; }
        Set<String> studentSkills = skillSet(loggedInSkills);
        StringBuilder out = new StringBuilder("Your skills: " + loggedInSkills + "\n\n");
        for (String[] in : internships) {
            Set<String> required = skillSet(in[2]);
            Set<String> missing = new LinkedHashSet<>(required);
            missing.removeAll(studentSkills);
            int matched = required.size() - missing.size();
            int percent = required.isEmpty() ? 0 : matched * 100 / required.size();
            out.append(in[0]).append(" — ").append(in[1]).append("\nMatch: ")
                .append(percent).append("%\n");
            out.append(missing.isEmpty() ? "Skill gaps: None\n" : "Skill gaps: " + String.join(", ", missing) + "\n");
            out.append("Stipend: ₹").append(in[3]).append("\n\n");
        }
        showText("Skill Match & Skill Gaps", out.toString());
    }

    private void showStudentDashboard() {
        JFrame frame = new JFrame("InternMatch | Student Dashboard");
        frame.setSize(1000, 750);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(this);
        frame.setResizable(false);

        JPanel main = new JPanel(new BorderLayout(0, 20));
        main.setBackground(BACKGROUND);
        main.setBorder(BorderFactory.createEmptyBorder(25, 35, 20, 35));

        JPanel nav = new JPanel(new BorderLayout());
        nav.setOpaque(false);
        JLabel logo = new JLabel("InternMatch.");
        logo.setFont(new Font("SansSerif", Font.BOLD, 28)); logo.setForeground(TURQUOISE);
        JLabel badge = new JLabel(" STUDENT PORTAL ");
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));
        badge.setForeground(TURQUOISE); badge.setOpaque(true); badge.setBackground(CARD);
        badge.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        nav.add(logo, BorderLayout.WEST); nav.add(badge, BorderLayout.EAST);

        JPanel welcome = new JPanel();
        welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));
        welcome.setBackground(CARD);
        welcome.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        JLabel greeting = centeredLabel("Welcome, " + loggedInName + "!", 28, WHITE, true);
        JLabel sub = centeredLabel("Discover opportunities. Build skills. Shape your future.", 14, MUTED, false);
        welcome.add(greeting); welcome.add(Box.createVerticalStrut(10)); welcome.add(sub);

        JPanel cards = new JPanel(new GridLayout(2, 2, 18, 18));
        cards.setOpaque(false);
        String[] titles = {"Explore Internships", "Skill Match", "My Applications", "Track Progress"};
        String[] descriptions = {"Discover available opportunities", "Compare skills and find gaps",
            "View your application status", "Follow your internship journey"};
        for (int i = 0; i < titles.length; i++) {
            final int choice = i;
            JPanel card = new JPanel(new BorderLayout(0, 12));
            card.setBackground(CARD);
            card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            JLabel title = new JLabel(titles[i]);
            title.setFont(new Font("SansSerif", Font.BOLD, 20)); title.setForeground(WHITE);
            JLabel desc = new JLabel("<html>" + descriptions[i] + "</html>");
            desc.setForeground(MUTED);
            JButton b = new JButton("Open  →"); styleButton(b);
            b.addActionListener(e -> {
                if (choice == 0) showInternshipsForStudent(frame);
                else if (choice == 1) showSkillMatch();
                else if (choice == 2) showApplications(false);
                else showProgress();
            });
            card.add(title, BorderLayout.NORTH); card.add(desc, BorderLayout.CENTER); card.add(b, BorderLayout.SOUTH);
            cards.add(card);
        }
        JPanel body = new JPanel(new BorderLayout(0, 20)); body.setOpaque(false);
        body.add(welcome, BorderLayout.NORTH); body.add(cards, BorderLayout.CENTER);
        JButton back = makeBackButton("← Back to Welcome", () -> { frame.dispose(); showWelcomeScreen(); });
        main.add(nav, BorderLayout.NORTH); main.add(body, BorderLayout.CENTER); main.add(back, BorderLayout.SOUTH);
        frame.setContentPane(main);
        frame.setVisible(true);
    }

    private void showInternshipsForStudent(JFrame parent) {
        if (internships.isEmpty()) { JOptionPane.showMessageDialog(parent, "No internships available yet."); return; }
        String[] choices = new String[internships.size()];
        for (int i = 0; i < internships.size(); i++) {
            String[] in = internships.get(i);
            choices[i] = in[0] + " — " + in[1] + " (₹" + in[3] + ")";
        }
        String selected = (String) JOptionPane.showInputDialog(parent, "Choose an internship to view or apply:",
            "Available Internships", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
        if (selected == null) return;
        int index = Arrays.asList(choices).indexOf(selected);
        String[] in = internships.get(index);
        Set<String> required = skillSet(in[2]), studentSkills = skillSet(loggedInSkills);
        Set<String> missing = new LinkedHashSet<>(required); missing.removeAll(studentSkills);
        int match = required.isEmpty() ? 0 : (required.size() - missing.size()) * 100 / required.size();
        String details = "Title: " + in[0] + "\nCompany: " + in[1] + "\nRequired skills: " + in[2]
            + "\nStipend: ₹" + in[3] + "\nYour match: " + match + "%"
            + "\nSkill gaps: " + (missing.isEmpty() ? "None" : String.join(", ", missing));
        Object[] options = {"Apply", "Close"};
        int action = JOptionPane.showOptionDialog(parent, details, "Internship Details",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (action == 0) {
            for (String[] app : applications) {
                if (app[0].equalsIgnoreCase(loggedInEmail) && Integer.parseInt(app[1]) == index) {
                    JOptionPane.showMessageDialog(parent, "You have already applied for this internship."); return;
                }
            }
            applications.add(new String[]{loggedInEmail, String.valueOf(index), "Applied", "0"});
            JOptionPane.showMessageDialog(parent, "Application submitted successfully!");
        }
    }

    private void showApplications(boolean adminView) {
        if (applications.isEmpty()) { message("No applications found."); return; }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < applications.size(); i++) {
            String[] a = applications.get(i);
            int idx = Integer.parseInt(a[1]);
            if (idx < 0 || idx >= internships.size()) continue;
            String[] in = internships.get(idx);
            if (adminView) {
                String name = a[0];
                for (String[] student : students) if (student[1].equalsIgnoreCase(a[0])) name = student[0] + " (" + a[0] + ")";
                out.append("Application ").append(i + 1).append("\nStudent: ").append(name).append("\n");
            } else if (!a[0].equalsIgnoreCase(loggedInEmail)) continue;
            out.append("Internship: ").append(in[0]).append(" — ").append(in[1])
                .append("\nStatus: ").append(a[2]).append("\nProgress: ").append(a[3]).append("%\n\n");
        }
        if (out.length() == 0) out.append("No applications found for your account.");
        showText(adminView ? "Student Applications" : "My Applications", out.toString());
    }

    private void updateApplicationStatus() {
        if (applications.isEmpty()) { message("No applications to update."); return; }
        String[] choices = new String[applications.size()];
        for (int i = 0; i < applications.size(); i++) {
            String[] a = applications.get(i);
            String title = internships.get(Integer.parseInt(a[1]))[0];
            choices[i] = a[0] + " — " + title + " [" + a[2] + "]";
        }
        String chosen = (String) JOptionPane.showInputDialog(this, "Select application:",
            "Update Status", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
        if (chosen == null) return;
        int index = Arrays.asList(choices).indexOf(chosen);
        String[] statuses = {"Applied", "Under Review", "Shortlisted", "Selected", "Rejected"};
        String status = (String) JOptionPane.showInputDialog(this, "Choose new status:",
            "Update Status", JOptionPane.PLAIN_MESSAGE, null, statuses, applications.get(index)[2]);
        if (status == null) return;
        applications.get(index)[2] = status;
        if (status.equals("Selected")) applications.get(index)[3] = "100";
        message("Application status updated.");
    }

    private void showProgress() {
        boolean found = false;
        StringBuilder out = new StringBuilder();
        for (String[] a : applications) {
            if (!a[0].equalsIgnoreCase(loggedInEmail)) continue;
            found = true;
            String[] in = internships.get(Integer.parseInt(a[1]));
            out.append(in[0]).append(" — ").append(in[1]).append("\nStatus: ")
                .append(a[2]).append("\nProgress: ").append(a[3]).append("%\n\n");
        }
        showText("Track Progress", found ? out.toString() : "You have no applications to track yet.");
    }

    private void showText(String title, String content) {
        JTextArea area = new JTextArea(content, 18, 45);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setCaretPosition(0);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void message(String text) {
        JOptionPane.showMessageDialog(this, text, "InternMatch", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InternMatchApp().setVisible(true));
    }
}
