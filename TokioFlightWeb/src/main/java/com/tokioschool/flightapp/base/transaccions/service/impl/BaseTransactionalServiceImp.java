package com.tokioschool.flightapp.base.transaccions.service.impl;

import com.tokioschool.flightapp.base.transaccions.domain.Base;
import com.tokioschool.flightapp.base.transaccions.repository.BaseDao;
import com.tokioschool.flightapp.base.transaccions.service.BaseTransactionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BaseTransactionalServiceImp implements BaseTransactionalService {

    private final BaseDao baseDao;

    /**
     * Método no transactional que:
     *  1. crea una nueva instancia de Base con el contador a 1.
     *  2. incremtna el contador a 1
     *  3. LO guarda.
     *  4. incrementa el contador de nuevo
     *  5. incrementa el contador de nuevo
     *
     * El resultado es de un registro con el contador a 2
     * el resto de operaciones no son persisitdas
     */
    @Override
    public void nonTransactionalWithOneCommit() {
        Base base = Base.builder().name("nonTransactionalWithOneCommit").count(1).build();
        base.setCount(base.getCount()+1);
        baseDao.save(base);
        base.setCount(base.getCount()+1);
        base.setCount(base.getCount()+1);
    }

    /**
     * Método no transactional que:
     *  1. crea una nueva instancia de Base con el contador a 1.
     *  2. incrementta el contador a 1
     *  3. Lo guarda (y asoacia a la instancia a un contexto de JPA o persistencia)
     *  4. incrementta el contador de nuevo
     *  5. Lo guarda ( no crea un registro nuevo, dado que el objeto pertence a un contexto)
     *
     * El resultado es de un registro con el contador a 3
     * donde se han realizado dos unidades de trabajo u operaciones transaccionales
     * diferentes sobre un mismo objeto
     *
     * Siendo la primea transacción la que asocial el objeto a un contexto de JPA
     *
     * Sin embargo, cada operación de persistencia, se trata
     * como una operación distinta.
     */
    @Override
    public void nonTransactionalWithMultipleCommit() {
        Base base = Base.builder().name("nonTransactionalWithMultipleCommit").count(1).build();

        base.setCount(base.getCount()+1);
        baseDao.save(base); // commit 1

        base.setCount(base.getCount()+1);
        baseDao.save(base); // commit 2
    }

    /**
     * Método transactional que:
     *  1. crea una nueva instancia de Base con el contador a 1.
     *  2. incrementta el contador a 1
     *  3. Lo guarda (y asoacia a la instancia a un contexto de JPA o persistencia)
     *  4. incrementta el contador de nuevo
     *  5. incrementta el contador de nuevo
     *  5. finaliza el bloque
     *
     * El resultado es de un registro con el contador a 4, donde realiza las siguientes
     * operaciones:
     * Hibernate: insert into bases (counter,created) values (?,?)
     * Hibernate: update bases set counter=?,created=? where id=?
     *
     * Esto se debe a que JPATransacctionalManager, detecta que duratne
     * la transacción o undiad dde trabajo se ha modfiicado el objeto de persistencia, por
     * lo que la úlitma operación sobre el (último estado de la entidad), es la foto que se persiste
     *
     * Realizando todas las operaciones de persisencia en un mismo bloque
     */
    @Override
    @Transactional
    public void transactionalWithOneCommit() {
        Base base = Base.builder().name("transactionalWithOneCommit").count(1).build();

        base.setCount(base.getCount()+1);
        baseDao.save(base); // commit 1

        base.setCount(base.getCount()+1);
        base.setCount(base.getCount()+1);

    }

    /**
     * Método transactional que:
     *  1. crea una nueva instancia de Base con el contador a 1.
     *  2. incrementta el contador a 1
     *  3. Lo guarda (y asoacia a la instancia a un contexto de JPA o persistencia)
     *  4. incrementta el contador de nuevo
     *  5. Lo guarda
     *  5. incrementta el contador de nuevo
     *  5. finaliza el bloque
     *
     * El resultado es de un registro con el contador a 4, donde realiza las siguientes
     * operaciones:
     * Hibernate: insert into bases (counter,created) values (?,?)
     * Hibernate: update bases set counter=?,created=? where id=?
     *
     * Como se puede detactar el commit del paso (5) no se realiza, que hay una
     * operación sobre el mismo objeto que cambia su estado y por tanto
     * JPATransacctionalManager es e que ejucta
     *
     * Realizando todas las operaciones de persisencia en un mismo bloque
     */
    @Override
    @Transactional
    public void transactionalWithMultipleCommit() {
        Base base = Base.builder().name("transactionalWithMultipleCommit").count(1).build();

        base.setCount(base.getCount()+1);
        baseDao.save(base); // commit 1

        base.setCount(base.getCount()+1);
        baseDao.save(base); // commit 2

        base.setCount(base.getCount()+1); // ultimo estado de la entidad

    }

    /**
     * Metodo no transaccioanl que relaiza tres operaciones
     * de persisentica, pero solo se ejecuta dos, dado
     * que se prodcue una excepción antes
     * de ejecutar la tercera.
     */
    @Override
    public void nonTransactionalWithException() {
        Base base = Base.builder().name("nonTransactionalWithException").count(1).build();
        baseDao.save(base); // commit 1

        base.setCount(base.getCount()+1);
        baseDao.save(base); // commit 2

        base.setCount(base.getCount()/0); // excepción
        baseDao.save(base); // commit 3, no se ejecuta
    }

    /**
     * Método no transaccional que no realiza ninguna operación al
     * producirse una excepción unchecked o RuntineExcpection
     * (hace rollback automático)
     */
    @Override
    @Transactional
    public void transactionalWithException() {
        Base base = Base.builder().name("transactionalWithException").count(1).build();
        baseDao.save(base); // commit 1

        base.setCount(base.getCount()+1);
        base.setCount(base.getCount()/0); // excepción
    }

    /**
     * Metodo transaccional que produce una excepción checked (chequea),
     * por lo que no hace rollback automático y se persite el úlitmo estado
     * de la entidad antes de la excepción.
     *
     * nota: en el ejemplo se convierte ArithmeticException a
     * IOException,ya que esta última es una excepción chequeada y ArithmeticException al
     * hereadad de RuntimeException y no Excepctión.
     *
     * Excepciones Checked: Objetos de la clase Exception o cualquier otra clase que hereda de ella,
     *    excepto si heredan de RuntimeException.
     * Excepciones Unchecked: Objetos de la clase RuntimeException o de cualquiera otra clase que herede de ella.
     *
     * @throws IOException
     */
    @Override
    @Transactional
    public void transactionalWithCheckedCommit() throws IOException {
        Base base = Base.builder().name("transactionalWithCheckedCommit").count(1).build();
        baseDao.save(base); // commit 1

        base.setCount(base.getCount()+1);
        try {
            base.setCount(base.getCount() / 0); // excepción
        }catch (ArithmeticException ae){
            throw new IOException(ae);
        }
    }

    /**
     * Hace rollback de toda la transaccicón cuando se produce
     * una excepción "unchecked" o chequeda de tipo IOException.class
     *
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = IOException.class)
    public void transactionalWithCheckedRollback() throws IOException {
        Base base = Base.builder().name("transactionalWithCheckedRollback").count(1).build();
        baseDao.save(base); // commit 1ç

        base.setCount(base.getCount()+1);
        try {
            base.setCount(base.getCount() / 0); // excepción
        }catch (ArithmeticException ae){
            throw new IOException(ae);
        }
    }
}
