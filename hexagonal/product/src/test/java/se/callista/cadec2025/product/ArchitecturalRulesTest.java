package se.callista.cadec2025.product;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.plantuml.rules.PlantUmlArchCondition.Configuration.consideringOnlyDependenciesInDiagram;
import static com.tngtech.archunit.library.plantuml.rules.PlantUmlArchCondition.adhereToPlantUmlDiagram;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import java.net.URL;

@AnalyzeClasses(packages = "se.callista.cadec2025.product")
public class ArchitecturalRulesTest {

  static URL externalDependencyDiagram =ArchitecturalRulesTest.class
      .getResource("/external-dependencies.puml");
  static URL internalDependencyDiagram = ArchitecturalRulesTest.class
      .getResource("/internal-dependencies.puml");

  @ArchTest
  public static final ArchRule externalDependencies = classes()
      .that().resideInAPackage("..product..")
      .should(adhereToPlantUmlDiagram(externalDependencyDiagram, consideringOnlyDependenciesInDiagram()));

  @ArchTest
  public static final ArchRule internalDependencies = classes()
      .that().resideInAnyPackage("..application..")
      .should(adhereToPlantUmlDiagram(internalDependencyDiagram, consideringOnlyDependenciesInDiagram()));

}
