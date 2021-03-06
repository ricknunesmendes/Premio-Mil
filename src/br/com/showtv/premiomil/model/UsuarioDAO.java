package br.com.showtv.premiomil.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * @Author: rique
 */

public class UsuarioDAO {

	private static final String PERSISTENCE_UNIT = "premiomil";

	public void salvar(Usuario usuario) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.persist(usuario);
		manager.getTransaction().commit();
		manager.close();
		factory.close();
	}

	public List<Usuario> listar(Usuario usuario) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager manager = factory.createEntityManager();
		Query query = null;

		String nome = usuario != null ? usuario.getNome() : "";
		String email = usuario != null ? usuario.getEmail() : "";

		if (!nome.equals("") && email.equals("")) {
			query = manager.createQuery("FROM Usuario WHERE nome LIKE :paramNome ORDER BY email");
			query.setParameter("paramNome", "%" + nome + "%");
		} else if (nome.equals("") && !email.equals("")) {
			query = manager.createQuery("FROM Usuario WHERE email LIKE :paramEmail ORDER BY nome");
			query.setParameter("paramEmail", "%" + email + "%");
		} else if (!nome.equals("") && !email.equals("")) {
			query = manager
					.createQuery("FROM Usuario WHERE nome LIKE :paramNome AND email LIKE :paramEmail ORDER BY nome");
			query.setParameter("paramNome", "%" + nome + "%");
			query.setParameter("paramEmail", "%" + email + "%");
		} else {
			query = manager.createQuery("FROM Usuario ORDER BY nome");
		}

		List<Usuario> lista = query.getResultList();

		manager.close();
		factory.close();

		return lista;
	}

	public void alterar(Usuario usuario) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		manager.merge(usuario);
		manager.getTransaction().commit();
		manager.close();
		factory.close();
	}

	public Usuario buscarPorId(int id) {

		Usuario obj = null;

		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager manager = factory.createEntityManager();
		obj = manager.find(Usuario.class, id);
		manager.close();
		factory.close();

		return obj;
	}

	public void remover(int id) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager manager = factory.createEntityManager();
		Usuario usuario = manager.find(Usuario.class, id);

		manager.getTransaction().begin();
		manager.remove(usuario);
		manager.getTransaction().commit();

		manager.close();
		factory.close();

	}

}
