package executables;

import domain.model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import utils.JpaUtil;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class Executables {
    public class HibernateListar {
        public static void main(String[] args) {
            EntityManager em = JpaUtil.getEntityManager();
            List<Cliente> clientes = em.createQuery("select c from Cliente c",
                    Cliente.class).getResultList();
            clientes.forEach(System.out::println);
            em.close();
        }
    }
    public class HibernatePorId {
        public static void main(String[] args) {
            Scanner s = new Scanner(System.in);
            System.out.println("Ingrese el id: ");
            Long id = s.nextLong();
            EntityManager em = JpaUtil.getEntityManager();
            Cliente cliente = em.find(Cliente.class, id);
            System.out.println(cliente);
            Cliente cliente2 = em.find(Cliente.class, id);
            System.out.println(cliente2);
            em.close();
        }
    }
    public class HibernateResultListWhere {
        public static void main(String[] args) {
            Scanner s = new Scanner(System.in);
            EntityManager em = JpaUtil.getEntityManager();
            Query query = em.createQuery("select c from Cliente c where c.formaPago=?1", Cliente.class);
            System.out.println("Ingrese una forma de pago: ");
            String pago = s.next();
            query.setParameter(1, pago);
// query.setMaxResults(1);
            List<Cliente> clientes = query.getResultList();
            System.out.println(clientes);
            em.close();
        }
    }
    public class HibernateSingleResultWhere {
        public static void main(String[] args) {
            Scanner s = new Scanner(System.in);
            EntityManager em = JpaUtil.getEntityManager();
            Query query = em.createQuery("select c from Cliente c where c.formaPago=?1", Cliente.class);
            System.out.println("Ingrese una forma de pago: ");
            String pago = s.next();
            query.setParameter(1, pago);
            query.setMaxResults(1);
            Cliente c = (Cliente) query.getSingleResult();
            System.out.println(c);
            em.close();
        }
    }
    public class HibernateCrear {
        public static void main(String[] args) {
            EntityManager em = JpaUtil.getEntityManager();
            try {
                String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
                String apellido = JOptionPane.showInputDialog("Ingrese el apellido:");
                String pago = JOptionPane.showInputDialog("Ingrese la forma de pago:");
                em.getTransaction().begin();
                Cliente c = new Cliente();
                c.setNombre(nombre);
                c.setApellido(apellido);
                c.setFormaPago(pago);
                em.persist(c);
                em.getTransaction().commit();
                System.out.println("el id del cliente registrado es " +
                        c.getId());
                c = em.find(Cliente.class, c.getId());
                System.out.println(c);
            } catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
    }
    public class HibernateEliminar {
        public static void main(String[] args) {
            Scanner s = new Scanner(System.in);
            System.out.println("Ingrese el id del cliente a eliminar:");
            Long id = s.nextLong();
            EntityManager em = JpaUtil.getEntityManager();
            try {
                Cliente cliente = em.find(Cliente.class, id);
                em.getTransaction().begin();
                em.remove(cliente);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
    }

}
