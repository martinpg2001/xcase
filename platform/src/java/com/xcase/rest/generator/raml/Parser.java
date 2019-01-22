package com.xcase.rest.generator.raml;

import com.xcase.rest.generator.*;
import com.xcase.rest.generator.raml.events.*;
import com.xcase.rest.generator.raml.tokens.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import java.lang.invoke.MethodHandles;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Parser implements IParser
{
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private Stack<ParserState> states = new Stack<ParserState>();
    private TagDirectiveCollection tagDirectives = new TagDirectiveCollection();
    private ParserState state;
    private IScanner scanner;
    private ParsingEvent currentEvent;
    private Token currentToken;
    
    public ParsingEvent peek() {
        return currentEvent;
    }
    
    public ParsingEvent getCurrent() {
        return currentEvent;
    }

    private Token getCurrentToken() {
        if (currentToken == null) {
            while (scanner.moveNextWithoutConsuming()) {
                currentToken = scanner.getCurrent();
                com.xcase.rest.generator.raml.tokens.Comment commentToken = (com.xcase.rest.generator.raml.tokens.Comment) currentToken;
                if (commentToken != null) {
//                    pendingEvents.enqueue(new Comment(commentToken.Value, commentToken.IsInline, commentToken.Start, commentToken.End));
                    scanner.consumeCurrent();
                } else {
                    break;
                }
            }
        }

        return currentToken;
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="Parser"/> class.
    /// </summary>
    /// <param name="input">The input where the YAML stream instanceof  to be read.</param>
//    public Parser(TextReader input)
//    {
//        this(new Scanner(input));
//    }

    /// <summary>
    /// Initializes a new instance of the <see cref="Parser"/> class.
    /// </summary>
    public Parser(IScanner scanner)
    {
        this.scanner = scanner;
    }

    /// <summary>
    /// Gets the current event.
    /// </summary>
    public ParsingEvent Current;

    private EventQueue pendingEvents = new EventQueue();

    /// <summary>
    /// Moves to the next event.
    /// </summary>
    /// <returns>Returns true if there are more events available, otherwise returns false.</returns>
    public boolean moveNext()
    {
        // No events after the end of the stream or error.
        if (state == ParserState.StreamEnd)
        {
            currentEvent = null;
            return false;
        }
        else if (pendingEvents.Count == 0)
        {
            // Generate the next event.
//            pendingEvents.enqueue(StateMachine());
        }

//        currentEvent = pendingEvents.dequeue();
        return true;
    }

    private ParsingEvent StateMachine() throws Exception {
        switch (state)
        {
            /* 
            case StreamStart:
                return ParseStreamStart();

            case ImplicitDocumentStart:
                return ParseDocumentStart(true);

            case DocumentStart:
                return ParseDocumentStart(false);

            case DocumentContent:
                return ParseDocumentContent();

            case DocumentEnd:
                return ParseDocumentEnd();

            case BlockNode:
                return ParseNode(true, false);

            case BlockNodeOrIndentlessSequence:
                return ParseNode(true, true);

            case FlowNode:
                return ParseNode(false, false);

            case BlockSequenceFirstEntry:
                return ParseBlockSequenceEntry(true);

            case BlockSequenceEntry:
                return ParseBlockSequenceEntry(false);

            case IndentlessSequenceEntry:
                return ParseIndentlessSequenceEntry();

            case BlockMappingFirstKey:
                return ParseBlockMappingKey(true);

            case BlockMappingKey:
                return ParseBlockMappingKey(false);

            case BlockMappingValue:
                return ParseBlockMappingValue();

            case FlowSequenceFirstEntry:
                return ParseFlowSequenceEntry(true);

            case FlowSequenceEntry:
                return ParseFlowSequenceEntry(false);

            case FlowSequenceEntryMappingKey:
                return ParseFlowSequenceEntryMappingKey();

            case FlowSequenceEntryMappingValue:
                return ParseFlowSequenceEntryMappingValue();

            case FlowSequenceEntryMappingEnd:
                return ParseFlowSequenceEntryMappingEnd();

            case FlowMappingFirstKey:
                return ParseFlowMappingKey(true);

            case FlowMappingKey:
                return ParseFlowMappingKey(false);

            case FlowMappingValue:
                return ParseFlowMappingValue(false);

            case FlowMappingEmptyValue:
                return ParseFlowMappingValue(true);
            */
            
            default:
                LOGGER.error("unrecognized state " + state);
                throw new Exception();
        }
    }

    private void skip()
    {
        if (currentToken != null)
        {
            currentToken = null;
            scanner.consumeCurrent();
        }
    }

//    /// <summary>
//    /// Parse the production:
//    /// stream   ::= STREAM-START implicit_document? explicit_document* STREAM-END
//    ///              ************
//    /// </summary>
//    private ParsingEvent ParseStreamStart()
//    {
//        StreamStart streamStart = (StreamStart) GetCurrentToken();
//        if (streamStart == null)
//        {
//            var current = GetCurrentToken();
//            throw new SemanticErrorException(current.Start, current.End, "Did not find expected <stream-start>.");
//        }
//
//        Skip();
//
//        state = ParserState.ImplicitDocumentStart;
//        return new StreamStart(streamStart.Start, streamStart.End);
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// implicit_document    ::= block_node DOCUMENT-END*
//    ///                          *
//    /// explicit_document    ::= DIRECTIVE* DOCUMENT-START block_node? DOCUMENT-END*
//    ///                          *************************
//    /// </summary>
//    private ParsingEvent ParseDocumentStart(bool instanceof Implicit)
//    {
//        // Parse extra document end indicators.
//
//        if (!isImplicit)
//        {
//            while (GetCurrentToken() instanceof  DocumentEnd)
//            {
//                Skip();
//            }
//        }
//
//        // Parse an instanceof Implicit document.
//        if (isImplicit && !(GetCurrentToken() instanceof  VersionDirective || GetCurrentToken() instanceof  TagDirective || GetCurrentToken() instanceof  DocumentStart || GetCurrentToken() instanceof  StreamEnd))
//        {
//            var directives = new TagDirectiveCollection();
//            ProcessDirectives(directives);
//            states.Push(ParserState.DocumentEnd);
//            state = ParserState.BlockNode;
//            return new Events.DocumentStart(null, directives, true, GetCurrentToken().Start, GetCurrentToken().End);
//        }
//        else if (!(GetCurrentToken() instanceof  StreamEnd))
//        {
//            Mark start = GetCurrentToken().Start;
//            var directives = new TagDirectiveCollection();
//            var versionDirective = ProcessDirectives(directives);
//            var current = GetCurrentToken();
//            if (!(current instanceof  DocumentStart))
//            {
//                throw new SemanticErrorException(current.Start, current.End, "Did not find expected <document start>.");
//            }
//
//            states.Push(ParserState.DocumentEnd);
//            state = ParserState.DocumentContent;
//            ParsingEvent evt = new Events.DocumentStart(versionDirective, directives, false, start, current.End);
//            Skip();
//            return evt;
//        }
//        else
//        {
//            state = ParserState.StreamEnd;
//            ParsingEvent evt = new Events.StreamEnd(GetCurrentToken().Start, GetCurrentToken().End);
//            // Do not call skip here because that would throw an exception
//            if (scanner.MoveNextWithoutConsuming())
//            {
//                throw new InvalidOperationException("The scanner should contain no more tokens.");
//            }
//
//            return evt;
//        }
//    }
//
//    /// <summary>
//    /// Parse directives.
//    /// </summary>
//    private VersionDirective ProcessDirectives(TagDirectiveCollection tags)
//    {
//        VersionDirective version = null;
//        bool hasOwnDirectives = false;
//        while (true)
//        {
//            VersionDirective currentVersion;
//            TagDirective tag;
//            if ((currentVersion = GetCurrentToken() as VersionDirective) != null)
//            {
//                if (version != null)
//                {
//                    throw new SemanticErrorException(currentVersion.Start, currentVersion.End, "Found duplicate %YAML directive.");
//                }
//
//                if (currentVersion.Version.Major != Constants.MajorVersion || currentVersion.Version.Minor != Constants.MinorVersion)
//                {
//                    throw new SemanticErrorException(currentVersion.Start, currentVersion.End, "Found incompatible YAML document.");
//                }
//
//                version = currentVersion;
//                hasOwnDirectives = true;
//            }
//            else if ((tag = GetCurrentToken() as TagDirective) != null)
//            {
//                if (tags.Contains(tag.Handle))
//                {
//                    throw new SemanticErrorException(tag.Start, tag.End, "Found duplicate %TAG directive.");
//                }
//
//                tags.Add(tag);
//                hasOwnDirectives = true;
//            }
//            else
//            {
//                break;
//            }
//
//            Skip();
//        }
//
//        AddTagDirectives(tags, Constants.DefaultTagDirectives);
//        if (hasOwnDirectives)
//        {
//            tagDirectives.Clear();
//        }
//
//        AddTagDirectives(tagDirectives, tags);
//        return version;
//    }
//
//    private static void AddTagDirectives(TagDirectiveCollection directives, IEnumerable<TagDirective> source)
//    {
//        foreach (var directive in source)
//        {
//            if (!directives.Contains(directive))
//            {
//                directives.Add(directive);
//            }
//        }
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// explicit_document    ::= DIRECTIVE* DOCUMENT-START block_node? DOCUMENT-END*
//    ///                                                    ***********
//    /// </summary>
//    private ParsingEvent ParseDocumentContent()
//    {
//        if (
//            GetCurrentToken() instanceof  VersionDirective ||
//            GetCurrentToken() instanceof  TagDirective ||
//            GetCurrentToken() instanceof  DocumentStart ||
//            GetCurrentToken() instanceof  DocumentEnd ||
//            GetCurrentToken() instanceof  StreamEnd
//        )
//        {
//            state = states.Pop();
//            return ProcessEmptyScalar(scanner.CurrentPosition);
//        }
//        else
//        {
//            return ParseNode(true, false);
//        }
//    }
//
//    /// <summary>
//    /// Generate an empty scalar event.
//    /// </summary>
//    private static ParsingEvent ProcessEmptyScalar(Mark position)
//    {
//        return new Events.Scalar(null, null, string.Empty, ScalarStyle.Plain, true, false, position, position);
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// block_node_or_indentless_sequence    ::=
//    ///                          ALIAS
//    ///                          *****
//    ///                          | properties (block_content | indentless_block_sequence)?
//    ///                            **********  *
//    ///                          | block_content | indentless_block_sequence
//    ///                            *
//    /// block_node           ::= ALIAS
//    ///                          *****
//    ///                          | properties block_content?
//    ///                            ********** *
//    ///                          | block_content
//    ///                            *
//    /// flow_node            ::= ALIAS
//    ///                          *****
//    ///                          | properties flow_content?
//    ///                            ********** *
//    ///                          | flow_content
//    ///                            *
//    /// properties           ::= TAG ANCHOR? | ANCHOR TAG?
//    ///                          *************************
//    /// block_content        ::= block_collection | flow_collection | SCALAR
//    ///                                                               ******
//    /// flow_content         ::= flow_collection | SCALAR
//    ///                                            ******
//    /// </summary>
//    private ParsingEvent ParseNode(bool instanceof Block, bool instanceof IndentlessSequence)
//    {
//        var alias = GetCurrentToken() as AnchorAlias;
//        if (alias != null)
//        {
//            state = states.Pop();
//            ParsingEvent evt = new Events.AnchorAlias(alias.Value, alias.Start, alias.End);
//            Skip();
//            return evt;
//        }
//
//        Mark start = GetCurrentToken().Start;
//        Anchor anchor = null;
//        Tag tag = null;
//        // The anchor and the tag can be in any order. This loop repeats at most twice.
//        while (true)
//        {
//            if (anchor == null && (anchor = GetCurrentToken() as Anchor) != null)
//            {
//                Skip();
//            }
//            else if (tag == null && (tag = GetCurrentToken() as Tag) != null)
//            {
//                Skip();
//            }
//            else
//            {
//                break;
//            }
//        }
//
//        string tagName = null;
//        if (tag != null)
//        {
//            if (string.IsNullOrEmpty(tag.Handle))
//            {
//                tagName = tag.Suffix;
//            }
//            else if (tagDirectives.Contains(tag.Handle))
//            {
//                tagName = string.Concat(tagDirectives[tag.Handle].Prefix, tag.Suffix);
//            }
//            else
//            {
//                throw new SemanticErrorException(tag.Start, tag.End, "While parsing a node, find undefined tag handle.");
//            }
//        }
//
//        if (string.IsNullOrEmpty(tagName))
//        {
//            tagName = null;
//        }
//
//        string anchorName = anchor != null ? string.IsNullOrEmpty(anchor.Value) ? null : anchor.Value : null;
//        var instanceof Implicit = string.IsNullOrEmpty(tagName);
//        if (isIndentlessSequence && GetCurrentToken() instanceof  BlockEntry)
//        {
//            state = ParserState.IndentlessSequenceEntry;
//            return new Events.SequenceStart(
//                       anchorName,
//                       tagName,
//                       instanceof Implicit,
//                       SequenceStyle.Block,
//                       start,
//                       GetCurrentToken().End
//                   );
//        }
//        else
//        {
//            var scalar = GetCurrentToken() as Scalar;
//            if (scalar != null)
//            {
//                bool instanceof PlainImplicit = false;
//                bool instanceof QuotedImplicit = false;
//                if ((scalar.Style == ScalarStyle.Plain && tagName == null) || tagName == Constants.DefaultHandle)
//                {
//                    instanceof PlainImplicit = true;
//                }
//                else if (tagName == null)
//                {
//                    instanceof QuotedImplicit = true;
//                }
//
//                state = states.Pop();
//                ParsingEvent evt = new Events.Scalar(anchorName, tagName, scalar.Value, scalar.Style, instanceof PlainImplicit, instanceof QuotedImplicit, start, scalar.End);
//                Skip();
//                return evt;
//            }
//
//            var flowSequenceStart = GetCurrentToken() as FlowSequenceStart;
//            if (flowSequenceStart != null)
//            {
//                state = ParserState.FlowSequenceFirstEntry;
//                return new Events.SequenceStart(anchorName, tagName, instanceof Implicit, SequenceStyle.Flow, start, flowSequenceStart.End);
//            }
//
//            var flowMappingStart = GetCurrentToken() as FlowMappingStart;
//            if (flowMappingStart != null)
//            {
//                state = ParserState.FlowMappingFirstKey;
//                return new Events.MappingStart(anchorName, tagName, instanceof Implicit, MappingStyle.Flow, start, flowMappingStart.End);
//            }
//
//            if (isBlock)
//            {
//                var blockSequenceStart = GetCurrentToken() as BlockSequenceStart;
//                if (blockSequenceStart != null)
//                {
//                    state = ParserState.BlockSequenceFirstEntry;
//                    return new Events.SequenceStart(anchorName, tagName, instanceof Implicit, SequenceStyle.Block, start, blockSequenceStart.End);
//                }
//
//                var blockMappingStart = GetCurrentToken() as BlockMappingStart;
//                if (blockMappingStart != null)
//                {
//                    state = ParserState.BlockMappingFirstKey;
//                    return new Events.MappingStart(anchorName, tagName, instanceof Implicit, MappingStyle.Block, start, GetCurrentToken().End);
//                }
//            }
//
//            if (anchorName != null || tag != null)
//            {
//                state = states.Pop();
//                return new Events.Scalar(anchorName, tagName, string.Empty, ScalarStyle.Plain, instanceof Implicit, false, start, GetCurrentToken().End);
//            }
//
//            var current = GetCurrentToken();
//            throw new SemanticErrorException(current.Start, current.End, "While parsing a node, did not find expected node content.");
//        }
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// implicit_document    ::= block_node DOCUMENT-END*
//    ///                                     *************
//    /// explicit_document    ::= DIRECTIVE* DOCUMENT-START block_node? DOCUMENT-END*
//    ///                                                                *************
//    /// </summary>
//
//    private ParsingEvent ParseDocumentEnd()
//    {
//        bool instanceof Implicit = true;
//        Mark start = GetCurrentToken().Start;
//        Mark end = start;
//        if (GetCurrentToken() instanceof  DocumentEnd)
//        {
//            end = GetCurrentToken().End;
//            Skip();
//            instanceof Implicit = false;
//        }
//
//        state = ParserState.DocumentStart;
//        return new Events.DocumentEnd(isImplicit, start, end);
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// block_sequence ::= BLOCK-SEQUENCE-START (BLOCK-ENTRY block_node?)* BLOCK-END
//    ///                    ********************  *********** *             *********
//    /// </summary>
//
//    private ParsingEvent ParseBlockSequenceEntry(bool instanceof First)
//    {
//        if (isFirst)
//        {
//            GetCurrentToken();
//            Skip();
//        }
//
//        if (GetCurrentToken() instanceof  BlockEntry)
//        {
//            Mark mark = GetCurrentToken().End;
//            Skip();
//            if (!(GetCurrentToken() instanceof  BlockEntry || GetCurrentToken() instanceof  BlockEnd))
//            {
//                states.Push(ParserState.BlockSequenceEntry);
//                return ParseNode(true, false);
//            }
//            else
//            {
//                state = ParserState.BlockSequenceEntry;
//                return ProcessEmptyScalar(mark);
//            }
//        }
//        else if (GetCurrentToken() instanceof  BlockEnd)
//        {
//            state = states.Pop();
//            ParsingEvent evt = new Events.SequenceEnd(GetCurrentToken().Start, GetCurrentToken().End);
//            Skip();
//            return evt;
//        }
//        else
//        {
//            var current = GetCurrentToken();
//            throw new SemanticErrorException(current.Start, current.End, "While parsing a block collection, did not find expected '-' indicator.");
//        }
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// indentless_sequence  ::= (BLOCK-ENTRY block_node?)+
//    ///                           *********** *
//    /// </summary>
//    private ParsingEvent ParseIndentlessSequenceEntry()
//    {
//        if (GetCurrentToken() instanceof  BlockEntry)
//        {
//            Mark mark = GetCurrentToken().End;
//            Skip();
//            if (!(GetCurrentToken() instanceof  BlockEntry || GetCurrentToken() instanceof  Key || GetCurrentToken() instanceof  Value || GetCurrentToken() instanceof  BlockEnd))
//            {
//                states.Push(ParserState.IndentlessSequenceEntry);
//                return ParseNode(true, false);
//            }
//            else
//            {
//                state = ParserState.IndentlessSequenceEntry;
//                return ProcessEmptyScalar(mark);
//            }
//        }
//        else
//        {
//            state = states.Pop();
//            return new Events.SequenceEnd(GetCurrentToken().Start, GetCurrentToken().End);
//        }
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// block_mapping        ::= BLOCK-MAPPING_START
//    ///                          *******************
//    ///                          ((KEY block_node_or_indentless_sequence?)?
//    ///                            *** *
//    ///                          (VALUE block_node_or_indentless_sequence?)?)*
//    ///
//    ///                          BLOCK-END
//    ///                          *********
//    /// </summary>
//    private ParsingEvent ParseBlockMappingKey(bool instanceof First)
//    {
//        if (isFirst)
//        {
//            GetCurrentToken();
//            Skip();
//        }
//
//        if (GetCurrentToken() instanceof Key)
//        {
//            Mark mark = GetCurrentToken().End;
//            Skip();
//            if (!(GetCurrentToken() instanceof Key || GetCurrentToken() instanceof Value || GetCurrentToken() instanceof  BlockEnd))
//            {
//                states.Push(ParserState.BlockMappingValue);
//                return ParseNode(true, true);
//            }
//            else
//            {
//                state = ParserState.BlockMappingValue;
//                return ProcessEmptyScalar(mark);
//            }
//        }
//        else if (GetCurrentToken() instanceof BlockEnd)
//        {
//            state = states.Pop();
//            ParsingEvent evt = new Events.MappingEnd(GetCurrentToken().Start, GetCurrentToken().End);
//            Skip();
//            return evt;
//        }
//        else
//        {
//            var current = GetCurrentToken();
//            throw new SemanticErrorException(current.Start, current.End, "While parsing a block mapping, did not find expected key.");
//        }
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// block_mapping        ::= BLOCK-MAPPING_START
//    ///
//    ///                          ((KEY block_node_or_indentless_sequence?)?
//    ///
//    ///                          (VALUE block_node_or_indentless_sequence?)?)*
//    ///                           ***** *
//    ///                          BLOCK-END
//    ///
//    /// </summary>
//    private ParsingEvent ParseBlockMappingValue()
//    {
//        if (GetCurrentToken() instanceof  Value)
//        {
//            Mark mark = GetCurrentToken().End;
//            Skip();
//            if (!(GetCurrentToken() instanceof  Key || GetCurrentToken() instanceof  Value || GetCurrentToken() instanceof  BlockEnd))
//            {
//                states.Push(ParserState.BlockMappingKey);
//                return ParseNode(true, true);
//            }
//            else
//            {
//                state = ParserState.BlockMappingKey;
//                return ProcessEmptyScalar(mark);
//            }
//        }
//        else
//        {
//            state = ParserState.BlockMappingKey;
//            return ProcessEmptyScalar(GetCurrentToken().Start);
//        }
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// flow_sequence        ::= FLOW-SEQUENCE-START
//    ///                          *******************
//    ///                          (flow_sequence_entry FLOW-ENTRY)*
//    ///                           *                   **********
//    ///                          flow_sequence_entry?
//    ///                          *
//    ///                          FLOW-SEQUENCE-END
//    ///                          *****************
//    /// flow_sequence_entry  ::= flow_node | KEY flow_node? (VALUE flow_node?)?
//    ///                          *
//    /// </summary>
//    private ParsingEvent ParseFlowSequenceEntry(bool instanceof First)
//    {
//        if (isFirst)
//        {
//            GetCurrentToken();
//            Skip();
//        }
//
//        ParsingEvent evt;
//        if (!(GetCurrentToken() instanceof  FlowSequenceEnd))
//        {
//            if (!isFirst)
//            {
//                if (GetCurrentToken() instanceof  FlowEntry)
//                {
//                    Skip();
//                }
//                else
//                {
//                    var current = GetCurrentToken();
//                    throw new SemanticErrorException(current.Start, current.End, "While parsing a flow sequence, did not find expected ',' or ']'.");
//                }
//            }
//
//            if (GetCurrentToken() instanceof  Key)
//            {
//                state = ParserState.FlowSequenceEntryMappingKey;
//                evt = new Events.MappingStart(null, null, true, MappingStyle.Flow);
//                Skip();
//                return evt;
//            }
//            else if (!(GetCurrentToken() instanceof  FlowSequenceEnd))
//            {
//                states.Push(ParserState.FlowSequenceEntry);
//                return ParseNode(false, false);
//            }
//        }
//
//        state = states.Pop();
//        evt = new Events.SequenceEnd(GetCurrentToken().Start, GetCurrentToken().End);
//        Skip();
//        return evt;
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// flow_sequence_entry  ::= flow_node | KEY flow_node? (VALUE flow_node?)?
//    ///                                      *** *
//    /// </summary>
//    private ParsingEvent ParseFlowSequenceEntryMappingKey()
//    {
//        if (!(GetCurrentToken() instanceof  Value || GetCurrentToken() instanceof  FlowEntry || GetCurrentToken() instanceof  FlowSequenceEnd))
//        {
//            states.Push(ParserState.FlowSequenceEntryMappingValue);
//            return ParseNode(false, false);
//        }
//        else
//        {
//            Mark mark = GetCurrentToken().End;
//            Skip();
//            state = ParserState.FlowSequenceEntryMappingValue;
//            return ProcessEmptyScalar(mark);
//        }
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// flow_sequence_entry  ::= flow_node | KEY flow_node? (VALUE flow_node?)?
//    ///                                                      ***** *
//    /// </summary>
//    private ParsingEvent ParseFlowSequenceEntryMappingValue()
//    {
//        if (GetCurrentToken() instanceof  Value)
//        {
//            Skip();
//            if (!(GetCurrentToken() instanceof  FlowEntry || GetCurrentToken() instanceof  FlowSequenceEnd))
//            {
//                states.Push(ParserState.FlowSequenceEntryMappingEnd);
//                return ParseNode(false, false);
//            }
//        }
//
//        state = ParserState.FlowSequenceEntryMappingEnd;
//        return ProcessEmptyScalar(GetCurrentToken().Start);
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// flow_sequence_entry  ::= flow_node | KEY flow_node? (VALUE flow_node?)?
//    ///                                                                      *
//    /// </summary>
//    private ParsingEvent ParseFlowSequenceEntryMappingEnd()
//    {
//        state = ParserState.FlowSequenceEntry;
//        return new Events.MappingEnd(GetCurrentToken().Start, GetCurrentToken().End);
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// flow_mapping         ::= FLOW-MAPPING-START
//    ///                          ******************
//    ///                          (flow_mapping_entry FLOW-ENTRY)*
//    ///                           *                  **********
//    ///                          flow_mapping_entry?
//    ///                          ******************
//    ///                          FLOW-MAPPING-END
//    ///                          ****************
//    /// flow_mapping_entry   ::= flow_node | KEY flow_node? (VALUE flow_node?)?
//    ///                          *           *** *
//    /// </summary>
//    private ParsingEvent ParseFlowMappingKey(bool instanceof First)
//    {
//        if (isFirst)
//        {
//            GetCurrentToken();
//            Skip();
//        }
//
//        if (!(GetCurrentToken() instanceof  FlowMappingEnd))
//        {
//            if (!isFirst)
//            {
//                if (GetCurrentToken() instanceof  FlowEntry)
//                {
//                    Skip();
//                }
//                else
//                {
//                    var current = GetCurrentToken();
//                    throw new SemanticErrorException(current.Start, current.End, "While parsing a flow mapping,  did not find expected ',' or '}'.");
//                }
//            }
//
//            if (GetCurrentToken() instanceof  Key)
//            {
//                Skip();
//                if (!(GetCurrentToken() instanceof  Value || GetCurrentToken() instanceof  FlowEntry || GetCurrentToken() instanceof  FlowMappingEnd))
//                {
//                    states.Push(ParserState.FlowMappingValue);
//                    return ParseNode(false, false);
//                }
//                else
//                {
//                    state = ParserState.FlowMappingValue;
//                    return ProcessEmptyScalar(GetCurrentToken().Start);
//                }
//            }
//            else if (!(GetCurrentToken() instanceof  FlowMappingEnd))
//            {
//                states.Push(ParserState.FlowMappingEmptyValue);
//                return ParseNode(false, false);
//            }
//        }
//
//        state = states.Pop();
//        ParsingEvent evt = new Events.MappingEnd(GetCurrentToken().Start, GetCurrentToken().End);
//        Skip();
//        return evt;
//    }
//
//    /// <summary>
//    /// Parse the productions:
//    /// flow_mapping_entry   ::= flow_node | KEY flow_node? (VALUE flow_node?)?
//    ///                                   *                  ***** *
//    /// </summary>
//    private ParsingEvent ParseFlowMappingValue(bool instanceof Empty)
//    {
//        if (isEmpty)
//        {
//            state = ParserState.FlowMappingKey;
//            return ProcessEmptyScalar(GetCurrentToken().Start);
//        }
//
//        if (GetCurrentToken() instanceof  Value)
//        {
//            Skip();
//            if (!(GetCurrentToken() instanceof  FlowEntry || GetCurrentToken() instanceof  FlowMappingEnd))
//            {
//                states.Push(ParserState.FlowMappingKey);
//                return ParseNode(false, false);
//            }
//        }
//
//        state = ParserState.FlowMappingKey;
//        return ProcessEmptyScalar(GetCurrentToken().Start);
//    }

    private class EventQueue
    {
        // This class is specialized for our specific use case where there are exactly two priority levels.
        // If more levels are required, a more generic implementation should be used instead.
//        private Queue<ParsingEvent> highPriorityEvents = new Queue<ParsingEvent>();
//        private Queue<ParsingEvent> normalPriorityEvents = new Queue<ParsingEvent>();
        
        public void enqueue(ParsingEvent event)
        {
//            switch (@event.Type)
//            {
//                case EventType.StreamStart:
//                case EventType.DocumentStart:
//                    highPriorityEvents.Enqueue(@event);
//                    break;
//                default:
//                    normalPriorityEvents.Enqueue(@event);
//                    break;
//            }
        }

//        public ParsingEvent dequeue()
//        {
//            return highPriorityEvents.Count > 0
//                ? highPriorityEvents.dequeue()
//                : normalPriorityEvents.dequeue();
//        }

        public int Count;
    }
}
