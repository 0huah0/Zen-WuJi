package me.shizh.common.math;


public class MathCalculator {
	private final int NULL_MARK = 0; // 标记为空或者结束符
	private final int SEPARATION_MARK = 1; // 标记为分隔符
	private final int VAR_MARK = 2; // 标记为变量
	private final int NUM_MARK = 3; // 标记为数字
	private final int GRAMMAR_Exception = 0; // 语法错误
	private final int UNEND_Exception = 1; // 括号没有结束错误
	private final int NULLEXP_Exception = 2; // 表达式为空错误
	private final int BYZERO_Exception = 3; // 被0除错误
	private final String[] MESSAGES_Exception = { "语法错误", "大括号没有成对错误",
			"表达式为空错误", "被0除错误" };
	private final String END = "\0"; // 表达式的结束标记
	private String expre; // 表达式字符串
	private int position; // 求值器当前指针在表达式中的位置
	private String current; // 求值器当前处理的标记
	private int currentType; // 求值器当前处理标记的类型
	private double varArray[] = new double[26]; // 变量的数组

	/** 求值一个表达式，返回表达式的值。 */
	public Double analysis(String str) throws Exception {
		Double result;
		this.expre = str;
		this.position = 0;
		// 获取第一个标记
		this.getMark();
		if (this.current.equals(END)) {
			// 没有表达式异常
			this.proce_Exception(NULLEXP_Exception);
		}
		result = this.proce_Evalua(); // 处理赋值语句
		// 处理完赋值语句，应该就是表达式结束符，如果不是，则返回异常
		if (!this.current.equals(END)) {
			this.proce_Exception(GRAMMAR_Exception);
		}
		return result;
	}

	private double proce_Evalua() throws Exception {
		double result; // 结果
		int var_Index; // 变量下标
		String oldMark; // 旧标记
		int oldMarkType; // 旧标记的类型
		// 如果标记类型是变量
		if (this.currentType == VAR_MARK) {
			// 保存当前标记
			oldMark = new String(this.current);
			oldMarkType = this.currentType;
			// 取得变量的索引，本求值器只支持一个字目的变量，
			// 如果用户的变量字母长度大于1，则取第一个字母当作变量
			var_Index = Character.toUpperCase(this.current.charAt(0)) - 'A';
			// 获得下一个标记
			this.getMark();
			// 如果当前标记不是等号=
			if (!this.current.equals("=")) { // 判断当前表达式是否是赋值运算
				this.rollBack(); // 回滚
				// 不是一个赋值语句，将标记恢复到上一个标记
				this.current = new String(oldMark);
				this.currentType = oldMarkType;
			} else {
				// 如果当前标记是等号=，即给变量赋值，形式如a = 3 + 5;
				// 则计算等号后面表达式的值，然后将得到的值赋给变量
				this.getMark();
				// 因为加减法的优先级最低，所以计算加减法表达式。
				result = this.arith_AddExpre();
				// 将表达式的值赋给变量，并存在实例变量vars中。
				this.varArray[var_Index] = result;
				return result;
			}
		}
		return this.arith_AddExpre(); // 调用arith_AddExpre方法，进行加减法计算表达式的值。
	}

	private double arith_AddExpre() throws Exception {
		char op; // 操作符
		double result; // 结果
		double partialResult; // 当前子表达式的结果
		result = this.arith_MulExpre(); // 调用arith_MulExpre方法获取当前子表达式的值
		// 当前标记的第一个字母是加减号，则继续进行加减法运算。
		while ((op = this.current.charAt(0)) == '+' || op == '-') {
			this.getMark(); // 取下一个标记
			partialResult = this.arith_MulExpre(); // 调用arith_MulExpre方法获取当前子表达式的值
			switch (op) {
			case '-':
				// 如果是减法，则用已处理的子表达式的值减去当前子表达式的值
				result = result - partialResult;
				break;
			case '+':
				// 如果是加法，用已处理的子表达式的值加上当前子表达式的值
				result = result + partialResult;
				break;
			}
		}
		return result;
	}

	private double arith_MulExpre() throws Exception {
		char op; // 运算符
		double result; // 表达式结果
		double currentResult; // 子表达式的结果
		// 用指数运算计算当前子表达式的值
		result = this.indexExpre();
		// 如果当前标记的第一个字母是乘、除或者取模运算符，则继续进行乘除法运算。
		while ((op = this.current.charAt(0)) == '*' || op == '/' || op == '%') {
			this.getMark(); // 取下一个标记
			// 用指数运算计算当前子表达式的值
			currentResult = this.indexExpre();
			switch (op) {
			case '*':
				// 如果是乘法，则用已处理子表达式的值乘以当前子表达式的值
				result = result * currentResult;
				break;
			case '/':
				// 如果是除法，判断当前子表达式的值是否为0，如果为0，则抛出被0除异常
				// 除数不能为0
				if (currentResult == 0.0) {
					this.proce_Exception(BYZERO_Exception);
				}
				// 除数不为0，则进行除法运算
				result = result / currentResult;
				break;
			case '%':
				// 如果是取模运算，也要判断当前子表达式的值是否为0
				// 如果为0，则抛出被0除异常
				if (currentResult == 0.0) {
					this.proce_Exception(BYZERO_Exception);
				}
				// 进行取模运算
				result = result % currentResult;
				break;
			}
		}
		return result;
	}

	private double unaryOperator() throws Exception {
		double result; // 表达式结果
		String op; // 运算符
		op = "";
		// 如果当前标记类型为分隔符，而且分隔符的值等于+或者-。
		if ((this.currentType == SEPARATION_MARK) && this.current.equals("+")
				|| this.current.equals("-")) {
			op = this.current;
			this.getMark();
		}
		// 用括号运算计算当前子表达式的值
		result = this.parenthesis();
		if (op.equals("-")) {
			// 如果操作符为-，则表示负数，将子表达式的值变为负数
			result = -result;
		}
		return result;
	}

	private double parenthesis() throws Exception {
		double result; // 表达式结果
		if (this.current.equals("(")) { // 如果当前标记为左括号，则表示是一个括号运算
			this.getMark(); // 取下一个标记
			result = this.arith_AddExpre(); // 调用arith_AddExpre方法
			// 判断括圆括号是否匹配，如果当前标记不等于右括号，抛出括号不匹配异常
			if (!this.current.equals(")")) {
				this.proce_Exception(UNEND_Exception);
			}
			this.getMark(); // 否则取下一个标记
		} else {
			// 如果标记不是左括号，表示不是一个括号运算，则调用varNumber方法
			result = this.varNumber();
		}
		return result;
	}

	private double varNumber() throws Exception {
		double result = 0.0; // 结果
		switch (this.currentType) {
		case NUM_MARK:
			// 如果当前标记类型为数字
			try {
				// 将数字的字符串转换成数字值
				result = Double.parseDouble(this.current);
			} catch (NumberFormatException exc) {
				this.proce_Exception(GRAMMAR_Exception);
			}
			this.getMark(); // 取下一个标记
			break;
		case VAR_MARK:
			// 如果当前标记类型是变量，则取变量的值
			result = this.seekVar(current);
			this.getMark();
			break;
		default:
			this.proce_Exception(GRAMMAR_Exception);
			break;
		}
		return result;
	}

	private double seekVar(String vname) throws Exception {
		// 判断是否有语法异常发生，如果变量的第一个字符不是字母，则抛出语法异常
		if (!Character.isLetter(vname.charAt(0))) {
			proce_Exception(GRAMMAR_Exception);
			return 0.0;
		}
		// 从实例变量数组varArray中取出该变量的值
		return varArray[Character.toUpperCase(vname.charAt(0)) - 'A'];
	}

	private double indexExpre() throws Exception {
		double result; // 表达式结果
		double currentResult; // 子表达式的值
		double ex; // 指数的底数
		int t; // 指数的幂
		result = this.unaryOperator(); // 调用unaryOperator方法，获取当前表达式中的底数值
		if (this.current.equals("^")) { // 如果当前标记为"^"运算符，则为指数计算
			// 获取下一个标记，即获取指数的幂
			this.getMark();
			currentResult = this.indexExpre();
			ex = result;
			if (currentResult == 0.0) {
				// 如果指数的幂为0，则指数的值为1
				result = 1.0;
			} else {
				// 否则，指数的值为个数为指数幂的底数相乘的结果。
				for (t = (int) currentResult - 1; t > 0; t--) {
					result = result * ex;
				}
			}
		}
		return result;
	}

	private void rollBack() {
		if (this.current == END) {
			return;
		}
		// 求值器当前指针往前移动
		for (int i = 0; i < this.current.length(); i++) {
			this.position--;
		}
	}

	private void proce_Exception(int errorType) throws Exception {
		// 遇到异常情况时，根据错误类型，取得异常提示信息，将提示信息封装在异常中抛出
		throw new Exception(MESSAGES_Exception[errorType]);
	}

	private void getMark() {
		// 设置初始值
		this.currentType = NULL_MARK;
		this.current = "";
		// 判断表达式是否结束，如果求值器当前指针等于字符串的长度则表示表达式已经结束
		if (this.position == this.expre.length()) {
			this.current = END; // 如果表达式已经结束，则设置当前标记的置为END。
			return;
		}
		while (this.position < this.expre.length() // 当遇到表达式中的空白符则跳过
				&& Character.isWhitespace(this.expre.charAt(this.position))) {
			++this.position;
		}
		if (this.position == this.expre.length()) { // 判断当前表达式是否结束
			this.current = END;
			return;
		}
		char currentChar = this.expre.charAt(this.position); // 取得求值器当前指针指向的字符
		if (isSepara(currentChar)) { // 如果当前字符是一个分隔符，则认为这是一个分隔符标记，
			this.current += currentChar; // 给当前标记和标记类型赋值，并将指针后移
			this.position++;
			this.currentType = SEPARATION_MARK;
		} else if (Character.isLetter(currentChar)) {// 判断当前字符是否是一个字母
			while (!isSepara(currentChar)) { // 依次求值该变量的组成部分，直到遇到一个分隔符为止
				this.current += currentChar;
				this.position++;
				if (this.position >= this.expre.length()) {
					break;
				} else {
					currentChar = this.expre.charAt(this.position);
				}
			}
			this.currentType = VAR_MARK; // 设置标记类型为变量
		} else if (Character.isDigit(currentChar)) { // 判断当前字符是否是一个数字
			while (!isSepara(currentChar)) { // 依次求值该数字的组成部分，直到遇到一个分隔符为止
				this.current += currentChar;
				this.position++;
				if (this.position >= this.expre.length()) {
					break;
				} else {
					currentChar = this.expre.charAt(this.position);
				}
			}
			this.currentType = NUM_MARK; // 设置标记类型为数字
		} else {
			// 如果是无法识别的字符，则设置表达式结束
			this.current = END;
			return;
		}
	}

	private boolean isSepara(char c) {
		if ((" +-/*%^=()".indexOf(c) != -1))
			return true;
		return false;
	}

	public static void main(String[] args) throws Exception {
		MathCalculator calculator = new MathCalculator();
		System.out.println(calculator.analysis("2~4"));
		
		System.exit(0);
		
		double a = 15.0, b = 7.0;
		String express1 = "a = 15.0";
		System.out.println("表达式1：(\"a = 15.0\") = "+ calculator.analysis(express1));
		String express2 = "b = 7.0";
		System.out.println("表达式2：(\"b = 7.0\") = "+ calculator.analysis(express2));
		String express3 = "(a+b) * (a-b)";
		System.out.println("表达式3：(\"(" + a + "+" + b + ") * (" + a + "-" + b+ ")\") = " + calculator.analysis(express3));
		String express4 = "2*6-7/2";
		System.out.println("表达式4：(\"2*6-7/2\") = "+ calculator.analysis(express4));
		String express5 = "(8-3)*((a+b)/(a-b))";
		System.out.println("表达式5：(\"(8-3)*((" + a + "+" + "+" + b + ")/(" + a+ "-" + b + "))\") = " + calculator.analysis(express5));
		String express6 = "13 % 2";
		System.out.println("表达式6：(\"13%2\") = " + calculator.analysis(express6));
		String express7 = "4^3 * 8 + 2";
		System.out.println("表达式7：\"3^2 * 5 + 4\") = "+ calculator.analysis(express7));
	}
}
