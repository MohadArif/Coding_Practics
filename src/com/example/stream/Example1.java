package com.example.stream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Example1 {
    public static void main(String[] args) {

        List<Person> persons=List.of(
                new Person("Alice", 30, 50000.0, "Engineer"),
                new Person("Bob", 25, 60000.0, "Designer"),
                new Person("Charlie", 35, 70000.0, "Manager"),
                new Person("David", 28, 55000.0, "Engineer"),
                new Person("Eve", 32, 65000.0, "Designer")
        );

        /**
         *  🟢 Basic
         */
        // Example 1: Filter persons with age greater than 30
        persons.stream().filter(person->person.age()>30).forEach(System.out::println);

        //Q2. Find all persons whose salary is greater than ₹60,000.
        persons.stream().filter(person->person.salary()>60000).forEach(System.out::println);

        //Q3. Get only the names of all persons.
        persons.stream().map(Person::name).forEach(System.out::println);

        //Q4. Get all persons whose jobTitle is "Engineer".

        persons.stream().filter(person -> person.jobTitle()=="Engineer").forEach(System.out::println);

        //Q5. Count the total number of persons.
        long totalPerson = persons.stream().count();
        System.out.println("total person : "+totalPerson);

        /**
         * 🟡 Intermediate
         */

        //Q6. Find the person with the highest salary.
        Optional<Person> highestSalary = persons.stream().max(Comparator.comparing(Person::salary));
        highestSalary.ifPresent(System.out::println);

        Person max = persons.stream().max(Comparator.comparingDouble(Person::salary)).get();
        System.out.println("max salary ; "+max);

        //Q7. Find the person with the lowest salary.
        Person minsalary = persons.stream().min(Comparator.comparingDouble(Person::salary)).get();
        System.out.println("min salary : "+minsalary);

        persons.stream().min(Comparator.comparing(Person::salary)).ifPresent(System.out::println);

        //Q8. Find the second-highest salary.
        Optional<Person> first = persons.stream().sorted(Comparator.comparingDouble(Person::salary)
                .reversed()).skip(1).findFirst();
        first.ifPresent(System.out::println);

        //Q9. Find the average salary of all persons.
        persons.stream().mapToDouble(Person::salary).average().ifPresent(System.out::println);

        //Q10. Find the total salary of all persons.
        double sum = persons.stream().mapToDouble(Person::salary).sum();
        System.out.println("total salary : "+sum);

        //Q11. Find the average age of all persons.
        persons.stream().mapToInt(Person::age).average().ifPresent(System.out::println);

        //Q12. Sort persons by salary in ascending order.
        persons.stream().sorted(Comparator.comparingDouble(Person::salary)).forEach(System.out::println);

        //Q13. Sort persons by salary in descending order.
        persons.stream().sorted(Comparator.comparingDouble(Person::salary).reversed()).forEach(System.out::println);

        //Q14. Sort persons by age, and if age is the same, sort by salary.
        persons.stream().sorted(Comparator.comparing(Person::age).thenComparingDouble(Person::salary))
                .forEach(System.out::println);

        persons.stream()
                .sorted(Comparator.comparingInt(Person::age)
                        .thenComparing(
                                Comparator.comparingDouble(Person::salary).reversed()
                        ))
                .forEach(System.out::println);

//        🔴 Interview Level

//        Q15. Find the highest-paid person for each job title.
//
//                Expected concept:
//
//        Java Developer         -> Priya (90000)
//        Senior Java Developer  -> Rahul (80000)
//        Tester                 -> Amit (50000)
//        HR                     -> Neha (45000)
//        Architect              -> Ravi (100000)

        Map<String, Optional<Person>> collect = persons.stream().collect(Collectors
                .groupingBy(Person::jobTitle, Collectors.maxBy(Comparator.comparingDouble(Person::salary))));
        collect.forEach((job,salary)-> System.out.println(job+" -> "+salary));

//        Q16. Group persons by jobTitle.
//
//        Expected:
//
//        Java Developer -> [Arif, Priya, Ankit]
//        Tester         -> [Amit]
//        HR             -> [Neha]
//        Architect      -> [Ravi]

        Map<String, List<String>> collect1 = persons.stream().collect(Collectors
                .groupingBy(Person::jobTitle, Collectors.mapping(Person::name, Collectors.toList())));
        collect1.forEach((jobTitles,name)->System.out.println(jobTitles+" : "+name));

//        Q17. Find the average salary for each job title.

        Map<String, Double> averageSalaryForEachJob =
                persons.stream().collect(Collectors.groupingBy(Person::jobTitle, Collectors.averagingDouble(Person::salary)));
        averageSalaryForEachJob.forEach((job,salary)-> System.out.println(job+" -> "+salary));

//        Q18. Find the number of persons in each job title.

        Map<String, Long> totalEmpEachJob = persons.stream().collect(Collectors.groupingBy(Person::jobTitle, Collectors.counting()));
                totalEmpEachJob.forEach((job,no)-> System.out.println(job+" "+no));

//        Q19. Find the oldest person in each job title.
        Map<String, Optional<Person>> collect2 = persons.stream().collect(Collectors
                .groupingBy(Person::jobTitle, Collectors.maxBy(Comparator.comparingInt(Person::age))));
       // System.out.println(collect2);  // to remove optional use forEach
        collect2.forEach((job,person)-> System.out.println(job +" "+person.orElse(null)));

//        Q20. Find all persons whose salary is above the average salary.

        double averageSalry = persons.stream().mapToDouble(Person::salary).average().orElse(0);
        persons.stream().filter(person -> person.salary()>averageSalry).forEach(System.out::println);
//
//🔥 Advanced Interview Questions
//
//        Q21. Find the second-highest-paid person in the entire list.

          persons.stream().sorted(Comparator.comparingDouble(Person::salary).reversed()).skip(1).findFirst().ifPresent(
                  System.out::println
          );
//
//                Q22. Find the second-highest salary for each job title.
//
//                Q23. Find the job title having the highest average salary.
//
//        Q24. Find the youngest person among people earning more than ₹60,000.
//
//        Q25. Find the top 3 highest-paid persons.
//
//                Q26. Find the top 2 highest-paid persons from each job title.
//
//                Q27. Find the job title with the maximum number of persons.
//
//                Q28. Partition persons into two groups:
//
//        salary >= ₹60,000
//        salary < ₹60,000
//
//        Hint:
//
//        Collectors.partitioningBy(...)
//          💯 Real Interview Challenge
//
//        Q29. Find the highest-paid person in each job title, but only consider people whose age is greater than 25.
//
//        Q30. Find the job title whose employees have the highest average salary, and return the job title and average salary.
    }
}


record Person(String name,int age,double salary,String jobTitle){}