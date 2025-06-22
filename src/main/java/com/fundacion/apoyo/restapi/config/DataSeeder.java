package com.fundacion.apoyo.restapi.config;


import com.fundacion.apoyo.restapi.model.Aportador;
import com.fundacion.apoyo.restapi.model.Insumo;
import com.fundacion.apoyo.restapi.model.Resident;
import com.fundacion.apoyo.restapi.repository.AportadorRepository;
import com.fundacion.apoyo.restapi.repository.InsumoRepository;
import com.fundacion.apoyo.restapi.repository.ResidentRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ResidentRepository residenteRepository;
    private final InsumoRepository insumoRepository;
    private final AportadorRepository aportadorRepository;

    public DataSeeder(ResidentRepository residenteRepository, InsumoRepository insumoRepository, AportadorRepository aportadorRepository) {
        this.residenteRepository = residenteRepository;
        this.insumoRepository = insumoRepository;
        this.aportadorRepository = aportadorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Usamos un Faker con localización en español para datos más realistas
        Faker faker = new Faker(Locale.forLanguageTag("es-CL"));

        // Poblar Residentes solo si la tabla está vacía
        if (residenteRepository.count() == 0) {
            System.out.println("Poblando la base de datos con Residentes de prueba...");
            for (int i = 0; i < 40; i++) {
                Resident residente = new Resident();
                residente.setNombreCompleto(faker.name().fullName());
                residente.setFechaNacimiento(faker.date().birthday(65, 95));
                residente.setDatosPersonales("Familiar: " + faker.name().fullName() + " - Tel: " + faker.phoneNumber().cellPhone());
                residente.setEstadoSaludGeneral(faker.medical().diseaseName() + " en tratamiento.");
                residente.setMedicamentosActuales(faker.medical().medicineName() + " 8hrs, " + faker.medical().medicineName() + " 12hrs.");
                residente.setCuidadosEspeciales("Dieta blanda, asistencia en movilidad.");
                residenteRepository.save(residente);
            }
        }

        // Poblar Insumos solo si la tabla está vacía
        if (insumoRepository.count() == 0) {
            System.out.println("Poblando la base de datos con Insumos de prueba...");
            for (int i = 0; i < 100; i++) {
                Insumo insumo = new Insumo();
                insumo.setNombre(faker.medical().medicineName());
                insumo.setDescripcion("Insumo de uso general para cuidados médicos.");
                insumo.setStockActual(faker.number().numberBetween(10, 200));
                insumo.setUnidad(faker.options().option("cajas", "unidades", "frascos"));
                insumo.setFechaUltimaCompra(faker.date().past(365, TimeUnit.DAYS));
                insumoRepository.save(insumo);
            }
        }

        // Poblar Aportadores solo si la tabla está vacía
        if (aportadorRepository.count() == 0) {
            System.out.println("Poblando la base de datos con Aportadores de prueba...");
            for (int i = 0; i < 10; i++) {
                Aportador aportador = new Aportador();
                aportador.setNombre(faker.company().name());
                aportador.setDescripcion(faker.company().profession());
                aportador.setCantidadDonada(faker.number().randomDouble(2, 125000, 5000000));
                aportadorRepository.save(aportador);
            }
        }
    }
}