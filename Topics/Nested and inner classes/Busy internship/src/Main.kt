class Intern(val weeklyWorkload: Int) {
    val baseWorkload = 20

    class Salary {
        val basePay = 50
        val extraHoursPay = 2.8
    }

    val weeklySalary = with(Salary()) {
        basePay + (if (weeklyWorkload > baseWorkload) weeklyWorkload - baseWorkload else 0) * extraHoursPay
    }
}
