/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jdk.jshell;

import jdk.jshell.Snippet.Status;

/**
 * A description of a change to a Snippet. These are generated by direct changes
 * to state with {@link JShell#eval(java.lang.String) JShell.eval(String)} or
 * {@link JShell#drop(jdk.jshell.Snippet) JShell.drop(Snippet)},
 * or indirectly by these same methods as
 * dependencies change or Snippets are overwritten. For direct changes, the
 * {@link SnippetEvent#causeSnippet()} is {@code null}.
 * <p>
 * {@code SnippetEvent} is immutable: an access to
 * any of its methods will always return the same result.
 * and thus is thread-safe.
 *
 * @author Robert Field
 * @since 9
 */
public class SnippetEvent {

    SnippetEvent(Snippet snippet, Status previousStatus, Status status,
            boolean isSignatureChange, Snippet causeSnippet,
            String value, JShellException exception) {
        this.snippet = snippet;
        this.previousStatus = previousStatus;
        this.status = status;
        this.isSignatureChange = isSignatureChange;
        this.causeSnippet = causeSnippet;
        this.value = value;
        this.exception = exception;
    }

    private final Snippet snippet;
    private final Status previousStatus;
    private final Status status;
    private final boolean isSignatureChange;
    private final Snippet causeSnippet;
    private final String value;
    private final JShellException exception;

    /**
     * The Snippet which has changed
     * @return the return the Snippet whose {@code Status} has changed.
     */
    public Snippet snippet() {
        return snippet;
    }

    /**
     * The status before the transition. If this event describes a Snippet
     * creation return {@link Snippet.Status#NONEXISTENT NONEXISTENT}.
     * @return the previousStatus
     */
    public Status previousStatus() {
        return previousStatus;
    }

    /**
     * The after status. Note: this may be the same as the previous status (not
     * all changes cause a {@code Status} change.
     * @return the status
     */
    public Status status() {
        return status;
    }

    /**
     * Indicates whether the signature has changed. Coming in or out of
     * {@linkplain Status#isDefined() definition} is always a signature change.
     * An overwritten Snippet
     * {@link jdk.jshell.Snippet.Status#OVERWRITTEN (status == OVERWRITTEN)}
     * is always {@code false} as responsibility for the
     * definition has passed to the overwriting definition.
     *
     * @return {@code true} if the signature changed; otherwise {@code false}
     */
    public boolean isSignatureChange() {
        return isSignatureChange;
    }

    /**
     * Either the snippet whose change caused this update or
     * {@code null}. This returns {@code null} if this change is the
     * creation of a new Snippet via
     * {@link jdk.jshell.JShell#eval(java.lang.String) eval} or it is the
     * explicit drop of a Snippet with
     * {@link jdk.jshell.JShell#drop(jdk.jshell.Snippet) drop}.
     *
     * @return the Snippet which caused this change or {@code null} if
     * directly caused by an API action.
     */
    public Snippet causeSnippet() {
        return causeSnippet;
    }

    /**
     * An instance of {@link jdk.jshell.UnresolvedReferenceException}, if an unresolved reference was
     * encountered, or an instance of {@link jdk.jshell.EvalException} if an exception was thrown
     * during execution, otherwise {@code null}.
     * @return the exception or {@code null}.
     */
    public JShellException exception() {
        return exception;
    }

    /**
     * The result value of successful run. The value is null if not executed
     * or an exception was thrown.
     * @return the value or {@code null}.
     */
    public String value() {
        return value;
    }

    /**
     * Return a string representation of the event
     * @return a descriptive representation of the SnippetEvent
     */
    @Override
    public String toString() {
        return "SnippetEvent(snippet=" + snippet +
                ",previousStatus=" + previousStatus +
                ",status=" + status +
                ",isSignatureChange=" + isSignatureChange +
                ",causeSnippet" + causeSnippet +
                (value == null? "" : "value=" + value) +
                (exception == null? "" : "exception=" + exception) +
                ")";
    }
}
