import app
import math

class InvalidPermissions(Exception):
    pass


class Calculator:
    #suma
    def add(self, x, y):
        self.check_types(x, y)
        return x + y
    #resta
    def substract(self, x, y):
        self.check_types(x, y)
        return x - y
    #multiplicación
    def multiply(self, x, y):
        if not app.util.validate_permissions(f"{x} * {y}", "User1"):
            raise InvalidPermissions('User has no permissions')

        self.check_types(x, y)
        return x * y
    #división
    def divide(self, x, y):
        self.check_types(x, y)
        if y == 0:
            raise TypeError("Division by zero is not possible")
        return x / y
    #potenciación
    def power(self, x, y):
        self.check_types(x, y)
        return x ** y
    #resto
    def remainder (self, x, y):
        self.check_types(x, y)
        if y == 0:
            raise TypeError("Division by zero is not possible 2")
        return x % y
    #raíz cuadrada
    def squareroot (self, x):
        self.check_types_non_y(x)
        if x < 0:
            raise TypeError("El valor debe ser igual o mayor que 0")
        return math.sqrt(x)
    #función logaritmo en base 10
    def logarithm10 (self, x):
        self.check_types_non_y(x)
        if x <= 0:
            raise TypeError("El valor debe ser mayor que 0")
        return math.log10(x)
    #verificación de tipos (integer/float) del valor dado de x e y
    def check_types(self, x, y):
        if not isinstance(x, (int, float)) or not isinstance(y, (int, float)):
            raise TypeError("Parameters must be numbers")
    #verificación de tipos (integer/float) del valor dado de x
    def check_types_non_y(self, x):
        if not isinstance(x, (int, float)):
            raise TypeError("Parameters must be numbers")


if __name__ == "__main__":  # pragma: no cover
    calc = Calculator()
    resultadd = calc.add(2,1)
    resultsubstract = calc.substract(2,1)
    resultdivide = calc.divide(8,2)
    resultpower = calc.power(2,4)
    resultremainder = calc.remainder(2,1)
    resultsquareroot = calc.squareroot(9)
    resultlogarithm10 = calc.logarithm10(2)

    print(resultadd, resultsubstract, resultdivide, resultpower, resultremainder, resultsquareroot, resultlogarithm10)
