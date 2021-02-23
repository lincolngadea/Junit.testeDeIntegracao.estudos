package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach(){
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void cleanUp(){
        em.getTransaction().rollback();
    }

    @Test
    void deveEncontrarUsuarioCadastrado() {
        Usuario usuario = criarUsuario();
        Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(encontrado);
    }

    @Test
    void naoDeveEncontrarUsuarioCadastrado() {
        criarUsuario();
        Assert.assertThrows(NoResultException.class,
                ()-> this.dao.buscarPorUsername("Beltrano"));
    }

    @Test
    void deveRemoverUmUsuario(){
        Usuario usuario = criarUsuario();
        dao.deletar(usuario);

        Assert.assertThrows(NoResultException.class,
                ()-> this.dao.buscarPorUsername(usuario.getNome()));
    }

    private Usuario criarUsuario(){
        Usuario usuario = new Usuario("Fulano", "fulano@email.com","12345678");
        em.persist(usuario);
        return usuario;
    }
}