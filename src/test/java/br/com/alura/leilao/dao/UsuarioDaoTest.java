package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

class UsuarioDaoTest {

    private UsuarioDao dao;

    @Test
    void deveEncontrarUsuarioCadastrado() {
        EntityManager em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);

        Usuario usuario = new Usuario("Fulano", "fulano@email.com","12345678");

        em.getTransaction().begin();
        em.createQuery("DELETE FROM Usuario").executeUpdate();
        em.persist(usuario);
        em.getTransaction().commit();

        Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(encontrado);
    }

    @Test
    void naoDeveEncontrarUsuarioCadastrado() {
        EntityManager em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);

        Usuario usuario = new Usuario("Fulano", "fulano@email.com","12345678");
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Usuario").executeUpdate();
        em.persist(usuario);
        em.getTransaction().commit();

        Assert.assertThrows(NoResultException.class,
                ()-> this.dao.buscarPorUsername("Beltrano"));
    }
}