package meetifai.service

import org.eclipse.rdf4j.model.Model
import org.eclipse.rdf4j.model.impl.SimpleValueFactory
import org.eclipse.rdf4j.model.util.ModelBuilder
import org.springframework.stereotype.Service

@Service
class LabService {

    fun getLabMappingModel(fileName: String = "lab") : Model {
        val vf = SimpleValueFactory.getInstance()
        val logicalSource = vf.createBNode()
        val subjectMap = vf.createBNode()
        val predicateObjectMap = vf.createBNode()

        val m = ModelBuilder()
                .setNamespace("rr", "http://www.w3.org/ns/r2rml#")
                .setNamespace("rml", "http://semweb.mmlab.be/ns/rml#")
                .setNamespace("ql", "http://semweb.mmlab.be/ns/ql#")
                .setNamespace("rdfs", "http://www.w3.org/2000/01/rdf-schema#")
                .setNamespace("mtfai", "http://meetif.ai/")
                .subject("mtfai:LabMapping_$fileName")
                    .add("rml:logicalSource", logicalSource)
                    .add("rr:subjectMap", subjectMap)
                    .add("rr:predicateObjectMap", predicateObjectMap)
                .subject(logicalSource)
                    .add("rml:source", "$fileName.json")
                    .add("rml:referenceFormulation", "ql:JSONPath")
                    .add("rml:iterator", "$.[*]")
                .subject(subjectMap)
                    .add("rr:template", "http://meetif.ai/{member.id}")
                .subject(predicateObjectMap)
                    .add("rr:predicate", "mtfai:participatedIn")
                    .add("rr:object", "mtfai:$fileName")
                .build()
        return m
    }
}