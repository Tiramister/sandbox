package spel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.IntUnaryOperator;
import org.example.spel.AppConfig;
import org.example.spel.Person;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

public class TestAll {
  @Test
  void simple() {
    ExpressionParser parser = new SpelExpressionParser();
    Expression expression = parser.parseExpression("1 * 10 + 1");
    assertEquals(11, expression.getValue(Integer.class)); // パース先の型を指定する
  }

  @Test
  void propertyAccess() {
    ExpressionParser parser = new SpelExpressionParser();
    Expression expression = parser.parseExpression("age");
    // age というプロパティへの参照になる
    // 何のオブジェクトのプロパティかは決まっていない

    Person person = new Person();
    expression.setValue(person, "20");
    // Integer ではなく String (SpEL ではない) を渡していることに注意
    // String からそのフィールドの型に変換してくれる

    assertEquals(20, person.age);
    assertEquals(20, expression.getValue(person));
  }

  @Test
  void variable() {
    EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
    context.setVariable("count", 100); // 変数 count を宣言
    context.setVariable("price", 399); // 変数 price を宣言

    ExpressionParser parser = new SpelExpressionParser();
    Expression expression = parser.parseExpression("#price * #count");
    assertEquals(39900, expression.getValue(context, Integer.class));
  }

  @Test
  void function() {
    EvaluationContext context = SimpleEvaluationContext.forReadWriteDataBinding().build();
    IntUnaryOperator square = (int x) -> x * x;
    context.setVariable("square", square); // 変数 count を宣言

    ExpressionParser parser = new SpelExpressionParser();
    Expression expression = parser.parseExpression("#square(3)");
    assertThrows(Exception.class, () -> expression.getValue(context, Integer.class));
  }

  @Test
  void injection() {
    var context = new AnnotationConfigApplicationContext(AppConfig.class);
    var person = context.getBean(Person.class);
    System.out.println("age: " + person.age);
  }
}
