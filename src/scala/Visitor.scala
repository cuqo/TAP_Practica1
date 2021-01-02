package scala

import part1.Message
import java.util.function.Predicate

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._


abstract class VisitorInterface {

  def visit(account: Account): Unit
  def visit(domain: Domain): Unit
}

class FilterVisitor(predicate: Predicate[Message]) extends VisitorInterface {
  var messages: ListBuffer[Message] = ListBuffer[Message]()

  override def visit(account: Account): Unit = {
     messages.addAll(account.mailbox.filterUserMail(predicate).asScala.toList)
  }

  override def visit(domain: Domain): Unit = {
    val message:ListBuffer[Message] = new ListBuffer[Message]
    val users:ListBuffer[Account] = domain.getUsers

    users.foreach(account => {
      message.addAll(account.mailbox.filterUserMail(predicate).asScala.toList)
    })
    messages = message
  }
}

class CounterVisitor() extends VisitorInterface {
  var users: Int = 0
  var domains: Int = 0

  override def visit(account: Account): Unit = {
    users+=1
  }

  override def visit(domain: Domain): Unit = {
    domain.childrenDomain.foreach(pos => pos match {
      case l: Domain =>
        visit(l)
        domains+=1
      case l: Account =>
        visit(l)
    })
  }
}

class FoldFilterVisitor[A](acc:A, op: (A, Message) => A, predCond: Predicate[Account]) extends VisitorInterface {
  var users:Map[String, A] = Map()

  override def visit(account: Account): Unit = {
    val list:List[Message] = account.getMail
    users+=(account.username -> list.foldLeft(acc)(op))
  }

  override def visit(domain: Domain): Unit = {
    domain.childrenDomain.foreach(pos => pos match {
      case l: Domain =>
        visit(l)
      case l: Account =>
        if(predCond.test(l)) {
          visit(l)
        }
    })
  }
}

class TransformerVisitor(op: List[Message] => List[Message]) extends VisitorInterface {
  var messages: ListBuffer[Message] = ListBuffer[Message]()
  var m:List[Message] = List()
  override def visit(account: Account): Unit = {
    m = op(Nil)
    messages.addAll(m)
  }

  override def visit(domain: Domain): Unit = {
    m = op(Nil)
    messages.addAll(m)
  }
}
