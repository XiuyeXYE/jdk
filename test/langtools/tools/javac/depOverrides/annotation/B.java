/* /nodynamiccopyright/ */
// combinations of methods defined in an interface
// and overridden in subtypes

// class should compile with deprecation warnings as shown

abstract class B extends A {
    @Deprecated public void iDep_aDep_bDep() { }
                public void iDep_aDep_bUnd() { } // warn
    //          public void iDep_aDep_bInh() { }
    @Deprecated public void iDep_aUnd_bDep() { }
                public void iDep_aUnd_bUnd() { }
    //          public void iDep_aUnd_bInh() { }
    @Deprecated public void iDep_aInh_bDep() { }
                public void iDep_aInh_bUnd() { } // warn
    //          public void iDep_aInh_bInh() { }
    @Deprecated public void iUnd_aDep_bDep() { }
                public void iUnd_aDep_bUnd() { } // warn
    //          public void iUnd_aDep_bInh() { }
    @Deprecated public void iUnd_aUnd_bDep() { }
                public void iUnd_aUnd_bUnd() { }
    //          public void iUnd_aUnd_bInh() { }
    @Deprecated public void iUnd_aInh_bDep() { }
                public void iUnd_aInh_bUnd() { }
    //          public void iUnd_aInh_bInh() { }
}
