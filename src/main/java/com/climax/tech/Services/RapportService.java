package com.climax.tech.Services;

import com.climax.tech.Entities.Client;
import com.climax.tech.Repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RapportService {
    private final ClientRepository clientRepository;

    public RapportService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


   public Resource generateClientsListtoPDF(String path) throws BadRequestException {
        try {
            ClassPathResource jrxmlResource = new ClassPathResource(path);
            InputStream jrxmlInputStream = jrxmlResource.getInputStream();
            List<Client> clientsList = clientRepository.findAll();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientsList);
            Map<String, Object> parameters = new HashMap<>();
           parameters.put("client", dataSource);
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return new ByteArrayResource(JasperExportManager.exportReportToPdf(jasperPrint));

        } catch (Exception e) {
            log.info("ERROR", e);
            throw new BadRequestException(e.getMessage());
        }
    }

    public byte[] generateClientsListToExcel(String path) throws BadRequestException {
        try {
            ClassPathResource jrxmlResource = new ClassPathResource(path);
            InputStream jrxmlInputStream = jrxmlResource.getInputStream();
            List<Client> clientsList = clientRepository.findAll();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(clientsList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("client", dataSource);

            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            JRXlsxExporter exporter = new JRXlsxExporter();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.exportReport();

            return outputStream.toByteArray();
        } catch (Exception e) {
            log.info("ERROR", e);
            throw new BadRequestException(e.getMessage());
        }
    }
}
