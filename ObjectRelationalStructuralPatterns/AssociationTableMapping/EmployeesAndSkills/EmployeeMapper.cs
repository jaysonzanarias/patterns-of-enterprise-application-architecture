class EmployeeMapper {
    public Employee Find(long id) {
        return (Employee) AbstractFind(id);
    }

    protected override void doLoad(DomainObject obj, DataRow row) {
        Employee emp = (Employee) obj;
        emp.Name = (String) row["name"];
        loadSkills(emp);
    }

    private IList loadSkills(Employee emp) {
        DataRow[] rows = skillLinkRows(emp);
        IList result = new ArrayList(); // didn't use?

        foreach(DataRow row in rows) {
            long skillID = (int) row["skillID"];
            emp.AddSkill(MapperRegistry.Skill.Find(skillID));

            // maybe?
            // result.add(MapperRegistry.Skill.Find(skillID));
        }

        return result;
    }

    private DataRow[] skillLinkRows(Employee emp) {
        String filter = String.Format("employeeID = {0}", emp.Id);
        return skillLinkTable.Select(filter);
    }

    private DataTable skillLinkTable {
        get {
            return dsh.Data.Tables["skillEmployees"];
        }
    }

    protected override void Save(DomainObject obj, DataRow row) {
        Employee emp = (Employee) obj;
        row["name"] = emp.Name;
        saveSkills(emp);
    }

    private void saveSkills(Employee emp) {
        deleteSkills(emp);
        foreach(Skill s in emp.Skills) {
            DataRow row = skillLinkTable.NewRow();
            row["employeeID"] = emp.Id;
            row["skillID"] = s.Id;
            skillLinkTable.Rows.Add(row);
        }
    }

    private void deleteSkills(Employee emp) {
        DataRow[] skillRows = skillLinkRows(emp);
        foreach(DataRow r in skillRows) {
            r.Delete();
        }
    }
}