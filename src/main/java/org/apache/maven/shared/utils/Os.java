package org.apache.maven.shared.utils;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/**
 * <p>Condition that tests the OS type.</p>
 *
 * <p>This class got copied over from Apache ANT.
 * Even the 'original' version from plexus-utils was
 * only an ANT fork!<br/>
 * The last time it got copied was on 2011-08-12</p>
 *
 * <p>When merging changes please take care of the special
 * OS_FAMILY handling in this version of Os.java!</p>
 *
 * @author Stefan Bodewig
 * @author Magesh Umasankar
 * @author Brian Fox
 * @author Mark Struberg
 * @since 1.0
 * @version $Revision$
 */
public class Os
{
    public static final String OS_NAME =
            System.getProperty( "os.name" ).toLowerCase( Locale.ENGLISH );
    public static final String OS_ARCH =
            System.getProperty( "os.arch" ).toLowerCase( Locale.ENGLISH );
    public static final String OS_VERSION =
            System.getProperty( "os.version" ).toLowerCase( Locale.ENGLISH );
    public static final String PATH_SEP =
            System.getProperty( "path.separator" );
    public static final String OS_FAMILY =
            getOsFamily();

    // store the valid families
    private static final Set validFamilies = getValidFamilies();


    /**
     * OS family to look for
     */
    private String family;
    /**
     * Name of OS
     */
    private String name;
    /**
     * version of OS
     */
    private String version;
    /**
     * OS architecture
     */
    private String arch;
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_WINDOWS = "windows";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_WIN9X = "win9x";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_NT = "winnt";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_OS2 = "os/2";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_NETWARE = "netware";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_DOS = "dos";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_MAC = "mac";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_TANDEM = "tandem";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_UNIX = "unix";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_OPENVMS = "openvms";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_ZOS = "z/os";
    /**
     * OS family that can be tested for. {@value}
     */
    public static final String FAMILY_OS400 = "os/400";

    /**
     * OpenJDK is reported to call MacOS X "Darwin"
     *
     * @see <a href="https://issues.apache.org/bugzilla/show_bug.cgi?id=44889">bugzilla issue</a>
     * @see <a href="https://issues.apache.org/jira/browse/HADOOP-3318">HADOOP-3318</a>
     */
    private static final String DARWIN = "darwin";


    /**
     * Initializes the set of valid families.
     */
    public static Set getValidFamilies()
    {
        if ( validFamilies != null )
        {
            return validFamilies;
        }

        Set valid = new HashSet();
        valid.add( FAMILY_DOS );
        valid.add( FAMILY_MAC );
        valid.add( FAMILY_NETWARE );
        valid.add( FAMILY_NT );
        valid.add( FAMILY_OPENVMS );
        valid.add( FAMILY_OS2 );
        valid.add( FAMILY_OS400 );
        valid.add( FAMILY_TANDEM );
        valid.add( FAMILY_UNIX );
        valid.add( FAMILY_WIN9X );
        valid.add( FAMILY_WINDOWS );
        valid.add( FAMILY_ZOS );

        return Collections.unmodifiableSet( valid );
    }

    /**
     * Default constructor
     */
    public Os()
    {
        //default
    }

    /**
     * Constructor that sets the family attribute
     *
     * @param family a String value
     */
    public Os( String family )
    {
        setFamily( family );
    }

    /**
     * Sets the desired OS family type
     *
     * @param f The OS family type desired<br />
     *          Possible values:<br />
     *          <ul>
     *          <li>dos</li>
     *          <li>mac</li>
     *          <li>netware</li>
     *          <li>os/2</li>
     *          <li>tandem</li>
     *          <li>unix</li>
     *          <li>windows</li>
     *          <li>win9x</li>
     *          <li>z/os</li>
     *          <li>os/400</li>
     *          </ul>
     */
    public void setFamily( String f )
    {
        family = f.toLowerCase( Locale.ENGLISH );
    }

    /**
     * Sets the desired OS name
     *
     * @param name The OS name
     */
    public void setName( String name )
    {
        this.name = name.toLowerCase( Locale.ENGLISH );
    }

    /**
     * Sets the desired OS architecture
     *
     * @param arch The OS architecture
     */
    public void setArch( String arch )
    {
        this.arch = arch.toLowerCase( Locale.ENGLISH );
    }

    /**
     * Sets the desired OS version
     *
     * @param version The OS version
     */
    public void setVersion( String version )
    {
        this.version = version.toLowerCase( Locale.ENGLISH );
    }

    /**
     * Determines if the OS on which Ant is executing matches the type of
     * that set in setFamily.
     *
     * @return true if the os matches.
     * @throws Exception if there is an error.
     * @see Os#setFamily(String)
     */
    public boolean eval() throws Exception
    {
        return isOs( family, name, arch, version );
    }

    /**
     * Determines if the OS on which Ant is executing matches the
     * given OS family.
     *
     * @param family the family to check for
     * @return true if the OS matches
     * @since 1.5
     */
    public static boolean isFamily( String family )
    {
        return isOs( family, null, null, null );
    }

    /**
     * Determines if the OS on which Ant is executing matches the
     * given OS name.
     *
     * @param name the OS name to check for
     * @return true if the OS matches
     * @since 1.7
     */
    public static boolean isName( String name )
    {
        return isOs( null, name, null, null );
    }

    /**
     * Determines if the OS on which Ant is executing matches the
     * given OS architecture.
     *
     * @param arch the OS architecture to check for
     * @return true if the OS matches
     * @since 1.7
     */
    public static boolean isArch( String arch )
    {
        return isOs( null, null, arch, null );
    }

    /**
     * Determines if the OS on which Ant is executing matches the
     * given OS version.
     *
     * @param version the OS version to check for
     * @return true if the OS matches
     * @since 1.7
     */
    public static boolean isVersion( String version )
    {
        return isOs( null, null, null, version );
    }

    /**
     * Determines if the OS on which Ant is executing matches the
     * given OS family, name, architecture and version
     *
     * @param family  The OS family
     * @param name    The OS name
     * @param arch    The OS architecture
     * @param version The OS version
     * @return true if the OS matches
     * @since 1.7
     */
    public static boolean isOs( String family, String name, String arch,
                                String version )
    {
        boolean retValue = false;

        if ( family != null || name != null || arch != null
                || version != null )
        {

            boolean isFamily = true;
            boolean isName = true;
            boolean isArch = true;
            boolean isVersion = true;

            if ( family != null )
            {

                //windows probing logic relies on the word 'windows' in
                //the OS
                boolean isWindows = OS_NAME.indexOf( FAMILY_WINDOWS ) > -1;
                boolean is9x = false;
                boolean isNT = false;
                if ( isWindows )
                {
                    //there are only four 9x platforms that we look for
                    is9x = (OS_NAME.indexOf( "95" ) >= 0
                            || OS_NAME.indexOf( "98" ) >= 0
                            || OS_NAME.indexOf( "me" ) >= 0
                            //wince isn't really 9x, but crippled enough to
                            //be a muchness. Ant doesnt run on CE, anyway.
                            || OS_NAME.indexOf( "ce" ) >= 0);
                    isNT = !is9x;
                }
                if ( family.equals( FAMILY_WINDOWS ) )
                {
                    isFamily = isWindows;
                }
                else if ( family.equals( FAMILY_WIN9X ) )
                {
                    isFamily = isWindows && is9x;
                }
                else if ( family.equals( FAMILY_NT ) )
                {
                    isFamily = isWindows && isNT;
                }
                else if ( family.equals( FAMILY_OS2 ) )
                {
                    isFamily = OS_NAME.indexOf( FAMILY_OS2 ) > -1;
                }
                else if ( family.equals( FAMILY_NETWARE ) )
                {
                    isFamily = OS_NAME.indexOf( FAMILY_NETWARE ) > -1;
                }
                else if ( family.equals( FAMILY_DOS ) )
                {
                    isFamily = PATH_SEP.equals( ";" ) && !isFamily( FAMILY_NETWARE );
                }
                else if ( family.equals( FAMILY_MAC ) )
                {
                    isFamily = OS_NAME.indexOf( FAMILY_MAC ) > -1
                            || OS_NAME.indexOf( DARWIN ) > -1;
                }
                else if ( family.equals( FAMILY_TANDEM ) )
                {
                    isFamily = OS_NAME.indexOf( "nonstop_kernel" ) > -1;
                }
                else if ( family.equals( FAMILY_UNIX ) )
                {
                    isFamily = PATH_SEP.equals( ":" )
                            && !isFamily( FAMILY_OPENVMS )
                            && (!isFamily( FAMILY_MAC ) || OS_NAME.endsWith( "x" )
                            || OS_NAME.indexOf( DARWIN ) > -1);
                }
                else if ( family.equals( FAMILY_ZOS ) )
                {
                    isFamily = OS_NAME.indexOf( FAMILY_ZOS ) > -1
                            || OS_NAME.indexOf( "os/390" ) > -1;
                }
                else if ( family.equals( FAMILY_OS400 ) )
                {
                    isFamily = OS_NAME.indexOf( FAMILY_OS400 ) > -1;
                }
                else if ( family.equals( FAMILY_OPENVMS ) )
                {
                    isFamily = OS_NAME.indexOf( FAMILY_OPENVMS ) > -1;
                }
                else
                {
                    throw new RuntimeException(
                            "Don\'t know how to detect os family \""
                                    + family + "\"" );
                }
            }
            if ( name != null )
            {
                isName = name.equals( OS_NAME );
            }
            if ( arch != null )
            {
                isArch = arch.equals( OS_ARCH );
            }
            if ( version != null )
            {
                isVersion = version.equals( OS_VERSION );
            }
            retValue = isFamily && isName && isArch && isVersion;
        }
        return retValue;
    }

    /**
     * Helper method to determine the current OS family.
     *
     * @return name of current OS family.
     */
    private static String getOsFamily()
    {
        Set families = getValidFamilies();

        Iterator iter = families.iterator();
        while ( iter.hasNext() )
        {
            String fam = (String) iter.next();
            if ( Os.isFamily( fam ) )
            {
                return fam;
            }
        }
        return null;
    }

    /**
     * Test if the given family String represents a valid Family
     * @param family
     * @return <code>true</code> if 'family' represents a valid OS-Familiy, <code>false</code> otherwise.
     */
    public static boolean isValidFamily( String family )
    {
        return validFamilies.contains( family );
    }


}
