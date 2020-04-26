package cz.muni.fi.pa165.dmbk.machinerental.service;

import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.model.Revision;
import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.repository.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Lukas Krekan
 * **/
@Service
public class RevisionServiceImpl implements RevisionService {

    @Autowired
    private RevisionRepository revisionRepository;

    @Override
    public Long createRevision(Revision revision) {
        var savedRevision = revisionRepository.saveAndFlush(revision);
        return savedRevision.getId();
    }

    @Override
    public void updateRevision(Revision revision) {
        revisionRepository.save(revision);
    }

    @Override
    public Optional<Revision> findById(Long id) {
        return revisionRepository.findById(id);
    }

    @Override
    public List<Revision> findAll() {
        return revisionRepository.findAll();
    }

    @Override
    public List<Revision> findAllByMachineId(Long machineId) {
        return revisionRepository.findAllByMachineId(machineId);
    }

    @Override
    public List<Revision> findAllByDate(LocalDate revisionDate) {
        return revisionRepository.findAllByRevisionDate(revisionDate);
    }

    @Override
    public List<Revision> findAllByDateAfter(LocalDate revisionDate) {
        return revisionRepository.findAllByRevisionDateAfter(revisionDate);
    }

    @Override
    public List<Revision> findAllByDateBefore(LocalDate revisionDate) {
        return revisionRepository.findAllByRevisionDateBefore(revisionDate);
    }

    @Override
    public List<Revision> findAllByDateBetween(LocalDate revisionDateFrom, LocalDate revisionDateTo) {
        return revisionRepository.findAllByRevisionDateBetween(revisionDateFrom, revisionDateTo);
    }

    @Override
    public void deleteById(Long id) {
        revisionRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        revisionRepository.deleteAll();
    }

    @Override
    public void setRevisionDate(Long id, LocalDate revisionDate) {
        var revision = revisionRepository.findById(id);
        if(revision.isPresent()){
            var foundRevision = revision.get();
            foundRevision.setRevisionDate(revisionDate);
            revisionRepository.saveAndFlush(foundRevision);
        }
    }

    @Override
    public void setRevisionNote(Long id, String note) {
        var revision = revisionRepository.findById(id);
        if(revision.isPresent()){
            var foundRevision = revision.get();
            foundRevision.setNote(note);
            revisionRepository.saveAndFlush(foundRevision);
        }
    }
}
